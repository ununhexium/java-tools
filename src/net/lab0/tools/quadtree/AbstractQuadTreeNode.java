package net.lab0.tools.quadtree;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.lab0.tools.geom.PointInterface;

/**
 * 
 * @author 116
 * 
 * @param <E>
 *            le type de données contenues dans cette node
 * 
 *            Conventions : <br/>
 *            une node est une feuille si children == null
 */
public abstract class AbstractQuadTreeNode<E extends PointInterface> implements QuadTreeNodeInterface<E>
{
    // Methodes abstraites
    protected abstract void getElementsOverlappingRectangle(double rectMaxX, double rectMaxY, double rectMinX, double rectMinY, Collection<E> collection);
    
    protected abstract QuadTreeNodeInterface<E> getElementNode(E e);
    
    /**
     * 
     * @param e
     * @return the zone in which the element <code>e</code> should be : NE, SE, NW, SW, SELF or OUTSIDE
     */
    protected abstract int getElementZone(E e);
    
    /**
     * 
     * @param e
     * @return <code>true</code> if the element is inside this node
     */
    protected abstract boolean isInbounds(E e);
    
    /**
     * Used to know whether an element in a node can be put in a smaller node
     * 
     * @param e
     *            the element to test
     * @return true if the element can be push down to a smaller node
     */
    protected abstract boolean canBePushedDown(E e);
    
    protected abstract AbstractQuadTreeNode<E> newNode(AbstractQuadTreeNode<E> abstractQuadTreeNode, int zone);
    
    /**
     * Les éléments contenus dans cette node. Si il existe des enfants à cette node, ce tableau ne contiendra aucun élément.
     */
    protected List<E>                       elements;
    
    /**
     * la racine de l'arbre
     */
    protected QuadTreeRoot<E>               treeRoot;
    
    /**
     * la node parent
     */
    protected AbstractQuadTreeNode<E>       parent;
    
    /**
     * Les enfants de cette node.
     */
    protected List<AbstractQuadTreeNode<E>> children   = null;
    
    /**
     * le point de séparation des éléments pour cette node
     */
    protected Point2D.Double                splitPoint = null;
    
    /**
     * les limites de cette node
     */
    
    protected double                        maxX;                // exclus de la zone de cette node
    protected double                        maxY;                // exclus de la zone de cette node
    protected double                        minX;                // inclus dans la zone de cette node
    protected double                        minY;                // inclus dans la zone de cette node
                                                                  
    /**
     * le niveau d'imbrication de cette node. La node racine a une profondeur de 0.
     */
    protected int                           depth;
    
    /**
     * la position dans la node parent, selon le schéma suivant
     * 
     * <pre>
     * +--------+--------+
     * | NW = 0 | NE = 1 |
     * +--------+--------+
     * | SW = 2 | SE = 3 |
     * +--------+--------+
     * 
     * root : 4
     * </pre>
     * 
     */
    protected int                           positionInParentNode;
    
    public AbstractQuadTreeNode(PointInterface p1, PointInterface p2)
    {
        this.depth = 0;
        this.parent = this;
        this.positionInParentNode = ROOT;
        
        if (treeRoot != null)
        {
            this.elements = new ArrayList<E>(treeRoot.maxElementsPerNode);
        }
        else
        {
            this.elements = new ArrayList<E>();
        }
        
        maxX = Math.max(p1.getX(), p2.getX());
        maxY = Math.max(p1.getY(), p2.getY());
        minX = Math.min(p1.getX(), p2.getX());
        minY = Math.min(p1.getY(), p2.getY());
        
        this.splitPoint = new Point2D.Double((maxX + minX) / 2, (maxY + minY) / 2);
    }
    
    public AbstractQuadTreeNode(QuadTreeRoot<E> treeRoot, PointInterface p1, PointInterface p2)
    {
        this.depth = 0;
        this.parent = this;
        this.treeRoot = treeRoot;
        this.positionInParentNode = ROOT;
        
        this.elements = new ArrayList<E>(treeRoot.maxElementsPerNode);
        
        maxX = Math.max(p1.getX(), p2.getX());
        maxY = Math.max(p1.getY(), p2.getY());
        minX = Math.min(p1.getX(), p2.getX());
        minY = Math.min(p1.getY(), p2.getY());
        
        this.splitPoint = new Point2D.Double((maxX + minX) / 2, (maxY + minY) / 2);
    }
    
    public AbstractQuadTreeNode(AbstractQuadTreeNode<E> parent, int positionInParentNode)
    {
        this.parent = parent;
        this.depth = parent.getDepth() + 1;
        this.treeRoot = parent.getTreeRoot();
        this.positionInParentNode = positionInParentNode;
        
        this.elements = new ArrayList<E>(treeRoot.maxElementsPerNode);
        
        switch (positionInParentNode)
        {
            case NW:
                maxX = parent.getSplitPoint().x;
                maxY = parent.getMaxY();
                minX = parent.getMinX();
                minY = parent.getSplitPoint().y;
                break;
            
            case NE:
                maxX = parent.getMaxX();
                maxY = parent.getMaxY();
                minX = parent.getSplitPoint().x;
                minY = parent.getSplitPoint().y;
                break;
            
            case SW:
                maxX = parent.getSplitPoint().x;
                maxY = parent.getSplitPoint().y;
                minX = parent.getMinX();
                minY = parent.getMinY();
                break;
            
            case SE:
                maxX = parent.getMaxX();
                maxY = parent.getSplitPoint().y;
                minX = parent.getSplitPoint().x;
                minY = parent.getMinY();
                break;
        }
        
        this.splitPoint = new Point2D.Double((maxX + minX) / 2, (maxY + minY) / 2);
    }
    
    // navigation dans le QuadTree. Adapté de http://www.developpez.net/forums/d476400/autres-langages/algorithmes/contribuez/java-structure-quadtree/
    
    // retourne le noeud voisin d'un noeud (algorithme générique)
    private QuadTreeNodeInterface<E> sibling(AbstractQuadTreeNode<E> node, int[] ancestortype, int[] reverter)
    {
        int[] path = new int[treeRoot.maxNodeDepth + 1];
        int pathlength = 0;
        
        // recherche du plus proche ancetre commun
        AbstractQuadTreeNode<E> ancestor = node;
        
        while (true)
        {
            if (ancestor.getPositionInParentNode() == ROOT)
            {
                return null; // no common ancestor -> exit
            }
            path[pathlength] = ancestor.getPositionInParentNode();
            pathlength++;
            if (ancestor.getPositionInParentNode() == ancestortype[0])
            {
                ancestor = ancestor.getParent();
                break;
            }
            if (ancestor.getPositionInParentNode() == ancestortype[1])
            {
                ancestor = ancestor.getParent();
                break;
            }
            ancestor = ancestor.getParent();
        }
        
        // parcours de l'arbre en utilisant le chemin symetrique
        AbstractQuadTreeNode<E> cursor = ancestor, next = null;
        for (int i = pathlength - 1; i >= 0; i--)
        {
            if (cursor.children == null)
                break;
            next = cursor.children.get(reverter[path[i]]);
            if (next == null)
                break;
            cursor = next;
        }
        return cursor;
    }
    
    // parcours reccursif des enfants. Helper pour la methode childrens()
    private void childrens_atom(List<QuadTreeNodeInterface<E>> results, AbstractQuadTreeNode<E> node, int[] finaltypes)
    {
        if (node == null)
            return;
        if (node.children == null)
        {
            results.add(node);
            return;
        }
        for (int type : finaltypes)
        {
            childrens_atom(results, node.children.get(type), finaltypes);
        }
    }
    
    // retourne la liste des feuilles accessibles à partir d'un noeud
    private List<QuadTreeNodeInterface<E>> childrens(AbstractQuadTreeNode<E> node, int[] finaltypes)
    {
        List<QuadTreeNodeInterface<E>> results = new ArrayList<QuadTreeNodeInterface<E>>();
        childrens_atom(results, node, finaltypes);
        return results;
    }
    
    // ----------------------------------------------------------------------------------
    
    /**
     * @return le noeud représentant la case de droite
     */
    public QuadTreeNodeInterface<E> getRightSibling()
    {
        return sibling(this, QuadTreeNodeInterface.rightAncestors, QuadTreeNodeInterface.horizontalReverter);
    }
    
    /**
     * @return le noeud représentant la case de gauche
     */
    public QuadTreeNodeInterface<E> getLeftSibling()
    {
        return sibling(this, QuadTreeNodeInterface.leftAncestors, QuadTreeNodeInterface.horizontalReverter);
    }
    
    /**
     * @return le noeud représentant la case du dessus
     */
    public QuadTreeNodeInterface<E> getTopSibling()
    {
        return sibling(this, QuadTreeNodeInterface.topAncestors, QuadTreeNodeInterface.verticalReverter);
    }
    
    /**
     * @return le noeud représentant la case du dessous
     */
    public QuadTreeNodeInterface<E> getBottomSibling()
    {
        return sibling(this, QuadTreeNodeInterface.bottomAncestors, QuadTreeNodeInterface.verticalReverter);
    }
    
    // ----------------------------------------------------------------------------------
    
    /**
     * @return toutes les feuilles de type: gauche
     */
    public List<QuadTreeNodeInterface<E>> getLeftChildren()
    {
        return childrens(this, QuadTreeNodeInterface.leftNodes);
    }
    
    /**
     * @return toutes les feuilles de type: droite
     */
    public List<QuadTreeNodeInterface<E>> getRightChildren()
    {
        return childrens(this, QuadTreeNodeInterface.rightNodes);
    }
    
    /**
     * @return toutes les feuilles de type: haut
     */
    public List<QuadTreeNodeInterface<E>> getTopChildren()
    {
        return childrens(this, QuadTreeNodeInterface.topNodes);
    }
    
    /**
     * @return toutes les feuilles de type: bas
     */
    public List<QuadTreeNodeInterface<E>> getBottomChildren()
    {
        return childrens(this, QuadTreeNodeInterface.bottomNodes);
    }
    
    /**
     * @return toutes les feuilles
     */
    public List<QuadTreeNodeInterface<E>> getLeaves()
    {
        return childrens(this, QuadTreeNodeInterface.zones);
    }
    
    // ----------------------------------------------------------------------------------
    
    /**
     * @return les noeuds représentant les cases voisines à gauche
     */
    public List<QuadTreeNodeInterface<E>> getRightNeighbors()
    {
        QuadTreeNodeInterface<E> sibling = this.getRightSibling();
        if (sibling == null)
        {
            return new ArrayList<QuadTreeNodeInterface<E>>();
        }
        return sibling.getLeftChildren();
    }
    
    /**
     * @return les noeuds représentant les cases voisines à droite
     */
    public List<QuadTreeNodeInterface<E>> getLeftNeighbors()
    {
        QuadTreeNodeInterface<E> sibling = this.getLeftSibling();
        if (sibling == null)
        {
            return new ArrayList<QuadTreeNodeInterface<E>>();
        }
        return sibling.getRightChildren();
    }
    
    /**
     * @return les noeuds représentant les cases voisines au dessus
     */
    public List<QuadTreeNodeInterface<E>> getTopNeighbors()
    {
        QuadTreeNodeInterface<E> sibling = this.getTopSibling();
        if (sibling == null)
        {
            return new ArrayList<QuadTreeNodeInterface<E>>();
        }
        return sibling.getBottomChildren();
    }
    
    /**
     * @return les noeuds représentant les cases voisines en dessous
     */
    public List<QuadTreeNodeInterface<E>> getBottomNeighbors()
    {
        QuadTreeNodeInterface<E> sibling = this.getBottomSibling();
        if (sibling == null)
        {
            return new ArrayList<QuadTreeNodeInterface<E>>();
        }
        return sibling.getTopChildren();
    }
    
    /**
     * 
     * @return le chemin pour accéder à cette node sous une forme du type (ROOT.)1.2.3.0.1.2. etc.
     */
    public String getPath()
    {
        if (parent == this)
        {
            return "" + positionInParentNode;
        }
        else
        {
            return parent.getPath() + "." + positionInParentNode;
        }
    }
    
    /**
     * 
     * @return le chemin pour accéder à cette node sous une forme du type (ROOT)123012 etc.
     */
    public String getCompactPath()
    {
        if (parent == this)
        {
            return "";
        }
        else
        {
            return parent.getCompactPath() + positionInParentNode;
        }
    }
    
    public List<? extends QuadTreeNodeInterface<E>> getChildren()
    {
        return Collections.unmodifiableList(children);
    }
    
    public QuadTreeRoot<E> getTreeRoot()
    {
        return treeRoot;
    }
    
    public AbstractQuadTreeNode<E> getParent()
    {
        return parent;
    }
    
    public Point2D.Double getSplitPoint()
    {
        return splitPoint;
    }
    
    public double getMaxX()
    {
        return maxX;
    }
    
    public double getMaxY()
    {
        return maxY;
    }
    
    public double getMinX()
    {
        return minX;
    }
    
    public double getMinY()
    {
        return minY;
    }
    
    public int getDepth()
    {
        return depth;
    }
    
    public int getPositionInParentNode()
    {
        return positionInParentNode;
    }
    
    public boolean isLeafNode()
    {
        return children == null;
    }
    
    /**
     * 
     * @return le nombre de nodes contenues dans celle-ci, celle-ci comprise
     */
    public int getNodesCount()
    {
        int total = 1; // this
        if (children != null)
        {
            for (QuadTreeNodeInterface<E> child : children)
            {
                total += child.getNodesCount();
            }
        }
        return total;
    }
    
    /**
     * Vide les listes contenant les éléments et met à <code>null</code> toutes les nodes enfant
     */
    public void clear()
    {
        if (children != null)
        {
            for (QuadTreeNodeInterface<E> child : children)
            {
                child.clear();
            }
            children = null;
        }
        // if (elements != null)
        // {
        elements.clear();
        // }
    }
    
    /**
     * Renvoie un itérateur pour les éléments que cette node contient. Ne permet pas de parcourir les éléments contenus dans les nodes enfants.
     */
    @Override
    public Iterator<E> iterator()
    {
        return elements.iterator();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return <code>true</code> si ce point est dans cette zone
     */
    protected boolean inbounds(double x, double y)
    {
        return (x >= minX && x < maxX && y >= minY && y < maxY);
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return la zone dans laquelle se situe le point (x;y)
     */
    private int getElementZone(double x, double y)
    {
        if (inbounds(x, y))
        {
            if (x >= splitPoint.x)
            {
                if (y >= splitPoint.y)
                {
                    return NE;
                }
                else
                {
                    return SE;
                }
            }
            else
            {
                if (y >= splitPoint.y)
                {
                    return NW;
                }
                else
                {
                    return SW;
                }
            }
        }
        else
        {
            return OUTSIDE;
        }
    }
    
    /**
     * 
     * @param p
     * @return la node feuille au point indiqué
     */
    protected QuadTreeNodeInterface<E> getLeafNodeAt(PointInterface p)
    {
        if (inbounds(p.getX(), p.getY()))
        {
            if (this.children == null)
            {
                return this;
            }
            else
            {
                return children.get(getElementZone(p.getX(), p.getY())).getLeafNodeAt(p);
            }
        }
        else
        {
            return null;
        }
    }
    
    /**
     * 
     * @return true si cette node ne contient aucun élément
     */
    public boolean isEmpty()
    {
        if (children == null)
        {
            return elements.isEmpty();
        }
        else
        // des nodes pourraient exister mais être vides -> vérifer que chaque node est vide
        {
            boolean ret = true;
            for (QuadTreeNodeInterface<E> child : children)
            {
                ret &= child.isEmpty();
                if (!ret)
                {
                    break;
                }
            }
            return ret;
        }
    }
    
    /**
     * 
     * @return Le nombre d'éléments dans cette node et ses enfants.
     */
    public int size()
    {
        if (this.children == null)
        {
            return elements.size();
        }
        else
        {
            int size = elements.size();
            for (QuadTreeNodeInterface<E> child : children)
            {
                size += child.size();
            }
            return size;
        }
    }
    
    /**
     * Ajoute cette node et tous ses enfants dans <code>collection</code>
     * 
     * @param collection
     */
    protected void getAllLeafNodes(Collection<QuadTreeNodeInterface<E>> collection)
    {
        if (children != null)
        {
            for (AbstractQuadTreeNode<E> child : children)
            {
                child.getAllLeafNodes(collection);
            }
        }
        else
        {
            collection.add(this);
        }
    }
    
    /**
     * Ajoute tous les éléments contenus dans cette node et ses enfants dans <code>collection</code>
     * 
     * @param collection
     */
    protected void getAllElements(Collection<E> collection)
    {
        collection.addAll(elements);
        if (children != null)
        {
            for (AbstractQuadTreeNode<E> child : children)
            {
                child.getAllElements(collection);
            }
        }
    }
    
    /**
     * Ajoute cette node et toutes les sous-nodes dans <code>collection</code>
     * 
     * @param collection
     */
    protected void getAllNodes(Collection<QuadTreeNodeInterface<E>> collection)
    {
        collection.add(this);
        
        if (children != null)
        {
            for (AbstractQuadTreeNode<E> child : children)
            {
                child.getAllNodes(collection);
            }
        }
    }
    
    /**
     * Renvoie dans <code>collection</code> toutes les nodes à la profondeur indiquée
     * 
     * @param collection
     *            la collection dans laquelleajouter les nodes
     * @param depth
     */
    protected void getAllNodesAtDepth(Collection<QuadTreeNodeInterface<E>> collection, int depth)
    {
        if (this.depth == depth)
        {
            collection.add(this);
        }
        else if (this.depth < depth)
        {
            if (children != null)
            {
                for (AbstractQuadTreeNode<E> child : children)
                {
                    child.getAllNodesAtDepth(collection, depth);
                }
            }
        }
    }
    
    /**
     * Ajoute toutes les nodes et sous-nodes contenues dans le rectangle défini par les 2 points à <code>collection</code>
     * 
     * @param rectMaxX
     * @param rectMaxY
     * @param rectMinX
     * @param rectMinY
     * @param collection
     *            la collection contenant le résultat
     */
    public void getNodesOverlappingRectangle(PointInterface p1, PointInterface p2, Collection<QuadTreeNodeInterface<E>> set)
    {
        double rectMaxX = Math.max(p1.getX(), p2.getX());
        double rectMaxY = Math.max(p1.getY(), p2.getY());
        double rectMinX = Math.min(p1.getX(), p2.getX());
        double rectMinY = Math.min(p1.getY(), p2.getY());
        
        getNodesOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY, set);
    }
    
    /**
     * Renvoie les zones de cette node qui touchent le rectangle défini par ses 4 bords
     * 
     * @param rectMaxX
     * @param rectMaxY
     * @param rectMinX
     * @param rectMinY
     * @return une liste des zones contenues dans le rectangle
     */
    protected ArrayList<Integer> getZonesOverlappingRectangle(double rectMaxX, double rectMaxY, double rectMinX, double rectMinY)
    {
        ArrayList<Integer> ret = new ArrayList<Integer>(4);
        
        if (splitPoint.x <= rectMinX) // le split est à gauche du rectangle
        {
            if (splitPoint.y <= rectMinY)
            {
                ret.add(NE);
            }
            else if (splitPoint.y > rectMaxY)
            {
                ret.add(SE);
            }
            else
            {
                ret.add(NE);
                ret.add(SE);
            }
        }
        else if (splitPoint.x > rectMaxX) // le split est à droite du rectangle
        {
            if (splitPoint.y <= rectMinY)
            {
                ret.add(NW);
            }
            else if (splitPoint.y > rectMaxY)
            {
                ret.add(SW);
            }
            else
            {
                ret.add(NW);
                ret.add(SW);
            }
        }
        else
        // le split est au dans le rectangle en x
        {
            if (splitPoint.y <= rectMinY)
            {
                ret.add(NW);
                ret.add(NE);
            }
            else if (splitPoint.y > rectMaxY)
            {
                ret.add(SW);
                ret.add(SE);
            }
            else
            {
                ret.add(NW);
                ret.add(NE);
                ret.add(SW);
                ret.add(SE);
            }
        }
        
        return ret;
    }
    
    /**
     * Ajoute toutes les nodes et sous-nodes contenues dans le rectangle défini par ses 4 bords à <code>collection</code>
     * 
     * @param rectMaxX
     * @param rectMaxY
     * @param rectMinX
     * @param rectMinY
     * @param collection
     *            la collection contenant le résultat
     */
    protected void getNodesOverlappingRectangle(double rectMaxX, double rectMaxY, double rectMinX, double rectMinY,
            Collection<QuadTreeNodeInterface<E>> collection)
    {
        // si la zone de cette node est entièrement contenue dans le rectangle
        if (this.minX >= rectMinX && this.maxX <= rectMaxX && this.maxY <= rectMaxY && this.minY >= rectMinY)
        {
            this.getAllNodes(collection);
        }
        else
        {
            // si la zone de cette node est partiellement contenue dans le rectangle
            if ((this.minX <= maxX || this.maxX >= minX) && (this.minY <= maxY || this.maxY >= minY))
            {
                collection.add(this);
            }
            // si la node a des enfants : tester lesquels sont dans le rectangle
            if (children != null)
            {
                for (int zone : getZonesOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY))
                {
                    children.get(zone).getNodesOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY, collection);
                }
            }
        }
    }
    
    /**
     * Supprime une instance de <code>e</code> de cette node. Utilise le QuadTree pour localiser l'objet à supprimer. Effectue un clear() sur le parent si il
     * s'agissait du dernier élément contenu dans la node parent
     * 
     * @param e
     *            L'objet à supprimer
     * @return <code>true</code> si l'élément a été supprimé
     */
    public boolean remove(E e)
    {
        if (e == null)
        {
            return false;
        }
        AbstractQuadTreeNode<E> node = (AbstractQuadTreeNode<E>) getElementNode(e);
        if (node == null)
        {
            return false;
        }
        else
        {
            boolean ret = node.elements.remove(e);
            
            // vérification : est ce que cet élément était le dernier élément contenu dans la node parent. Si oui : effacer les enfants dans le parent.
            if (parent.isEmpty())
            {
                parent.clear();
            }
            
            return ret;
        }
    }
    
    // used by tests
    protected void forceTreeSplit(int depth)
    {
        if (depth <= treeRoot.maxNodeDepth && this.depth < depth)
        {
            split();
            for (AbstractQuadTreeNode<E> child : children)
            {
                child.forceTreeSplit(depth);
            }
        }
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + " (" + getPath() + ") on root " + treeRoot.hashCode();
    }
    
    /**
     * Ajoute tous les éléments à cette node. Split si besoin.
     * 
     * @param elementsToAdd
     *            Les éléments à ajouter
     * @return
     */
    public boolean addAll(Collection<? extends E> elementsToAdd)
    {
        this.elements.addAll(elementsToAdd);
        
        if (this.depth < treeRoot.maxNodeDepth && this.elements.size() > treeRoot.maxElementsPerNode)
        {
            split();
        }
        
        return true;
    }
    
    /**
     * Ajoute un élément dans cet arbre. <b>Attention</b> méthode lourde. Préférer <code>addAll()</code> pour ajouter une grande quantité de données.
     * 
     * @param e
     *            L'élément à ajouter
     * @return true
     */
    public boolean add(E e)
    {
        if (e == null)
        {
            throw new NullPointerException("Elements can't be null.");
        }
        else if (isInbounds(e))
        {
            if (children == null || !canBePushedDown(e))
            {
                if (this.depth < treeRoot.maxNodeDepth && this.elements.size() + 1 > treeRoot.maxElementsPerNode)
                {
                    this.elements.add(e);
                    split();
                }
                else
                {
                    this.elements.add(e);
                }
            }
            else
            {
                children.get(getElementZone(e)).add(e);
            }
        }
        else
        {
            throw new IllegalArgumentException("Out of bounds");
        }
        
        return true;
    }
    
    /**
     * 
     * @param other
     * @return <code>true</code> if this node contains the <code>other</code> node
     */
    public boolean contains(QuadTreeNodeInterface<E> other)
    {
        QuadTreeNodeInterface<E> parent = other;
        
        while (parent != this)
        {
            // si root node
            if (parent == parent.getParent())
            {
                return false;
            }
            // on monte d'un niveau
            parent = parent.getParent();
        }
        
        return true;
    }
    
    /**
     * ajoute dans <code>collection</code> les points contenus dans le rectangle fermé <code>rect</code>.
     * 
     * @param rect
     *            le rectangle de selection
     * @param list
     *            la collection dans laquelle mettre les points trouvés
     */
    protected void getElementsInRectangle(PointInterface p1, PointInterface p2, Collection<E> collection)
    {
        // System.out.println("searching in : " + getNodePath());
        
        double rectMaxX = Math.max(p1.getX(), p2.getX());
        double rectMaxY = Math.max(p1.getY(), p2.getY());
        double rectMinX = Math.min(p1.getX(), p2.getX());
        double rectMinY = Math.min(p1.getY(), p2.getY());
        
        getElementsOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY, collection);
    }
    
    /**
     * 
     * @return le nombre d'éléments ajoutés à cette node ou un des ses enfants
     */
    public int split()
    {
        int added = 0;
        if (children == null)
        {
            children = new ArrayList<AbstractQuadTreeNode<E>>(4);
            for (int zone : zones)
            {
                AbstractQuadTreeNode<E> node = newNode(this, zone);
                children.add(node);
            }
        }
        
        ArrayList<E> nw = new ArrayList<E>();
        ArrayList<E> ne = new ArrayList<E>();
        ArrayList<E> sw = new ArrayList<E>();
        ArrayList<E> se = new ArrayList<E>();
        ArrayList<E> self = new ArrayList<E>();
        
        for (E p : elements)
        {
            switch (getElementZone(p))
            {
                case NW:
                    nw.add(p);
                    added++;
                    break;
                
                case NE:
                    ne.add(p);
                    added++;
                    break;
                
                case SW:
                    sw.add(p);
                    added++;
                    break;
                
                case SE:
                    se.add(p);
                    added++;
                    break;
                
                case SELF:
                    self.add(p);
                    added++;
                    break;
            }
        }
        
        elements.clear();
        
        children.get(NE).addAll(ne);
        children.get(NW).addAll(nw);
        children.get(SE).addAll(se);
        children.get(SW).addAll(sw);
        elements.addAll(self);
        
        return added;
    }
    
    protected void setTreeRoot(QuadTreeRoot<E> treeRoot)
    {
        this.treeRoot = treeRoot;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((splitPoint == null) ? 0 : splitPoint.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        AbstractQuadTreeNode<E> other = (AbstractQuadTreeNode<E>) obj;
        if (parent == null)
        {
            if (other.parent != null)
                return false;
        }
        else if (!parent.equals(other.parent))
            return false;
        if (splitPoint == null)
        {
            if (other.splitPoint != null)
                return false;
        }
        else if (!splitPoint.equals(other.splitPoint))
            return false;
        return true;
    }
    
    public AbstractQuadTreeNode<E> getNode(int position)
    {
        if (position >= 0 && position <= 3)
        {
            if (children != null)
            {
                return children.get(position);
            }
        }
        return null;
    }
}
