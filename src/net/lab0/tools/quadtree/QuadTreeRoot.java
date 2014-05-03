
package net.lab0.tools.quadtree;


import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.lab0.tools.geom.Point;
import net.lab0.tools.geom.PointInterface;


/**
 * Classe de la racine du quad tree. Les opérations d'implémentation de <code>Collection<QuadTreeNode></code> concernent les QuadTreeNode sauf mention contraire
 * 
 * @author 116
 * 
 */
public class QuadTreeRoot<E extends PointInterface>
extends AbstractCollection<E>
{
    protected AbstractQuadTreeNode<E> rootNode;
    protected int                     maxNodeDepth;
    protected int                     maxElementsPerNode;
    
    /**
     * Crée un QuadTree sur la zone définie par le rectangle contenant les deux points passés en paramètre.
     * 
     * @param maxElementsPerNode
     * @param maxNodeDepth
     * @param p1
     * @param p2
     * @throws IllegalArgumentException
     *             si les deux points sont les memes (x1=x2 et y1=y2)
     */
    public <T extends AbstractQuadTreeNode<E>> QuadTreeRoot(int maxElementsPerNode, int maxNodeDepth, T rootNode)
    {
        PointInterface p1 = new Point(rootNode.getMaxX(), rootNode.getMaxY());
        PointInterface p2 = new Point(rootNode.getMinX(), rootNode.getMinY());
        if (p1.equals(p2))
        {
            throw new IllegalArgumentException("Root node can't be a point");
        }
        this.maxElementsPerNode = maxElementsPerNode;
        this.maxNodeDepth = maxNodeDepth;
        this.rootNode = rootNode;
        rootNode.setTreeRoot(this);
    }
    
    public ArrayList<E> getElementsInRectangle(PointInterface p1, PointInterface p2)
    {
        ArrayList<E> list = new ArrayList<E>();
        rootNode.getElementsInRectangle(p1, p2, list);
        return list;
    }
    
    public ArrayList<E> getElementsInRectangle(double minX, double maxX, double minY, double maxY)
    {
        ArrayList<E> list = new ArrayList<E>();
        rootNode.getElementsOverlappingRectangle(maxX, maxY, minX, minY, list);
        return list;
    }
    
    public Iterator<E> iterator()
    {
        return new QuadTreeIterator<E>(this);
    }
    
    /**
     * 
     * @return Le nombre d'éléments de l'arbre.
     */
    public int size()
    {
        return rootNode.size();
    }
    
    /**
     * @return <code>true</code> si il existe au moins un element dans l'arbre. False sinon.
     */
    public boolean isEmpty()
    {
        return rootNode.isEmpty();
    }
    
    public boolean contains(Object o)
    {
        if (o == null)
        {
            return false;
        }
        else if (o instanceof PointInterface)
        {
            @SuppressWarnings("unchecked")
            boolean ret = contains((E) o);
            if (ret)
            {
                return true;
            }
        }
        
        return super.contains(o);
    }
    
    /**
     * Recherche dans l'arbre l'élément <code>e</code> en s'aidant de la position pour accélérer la recherche.
     * 
     * @param e
     *            l'élément à chercher
     * @return <code>true</code> si il existe un élément dans l'arbre qui soit égal à cet élément et que les deux se situent dans la même feuille.
     */
    public boolean contains(E e1)
    {
        for (E e2 : this)
        {
            if (e2.equals(e1))
            {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean remove(Object o)
    {
        if (o instanceof PointInterface)
        {
            return remove(o);
        }
        else
        {
            return super.remove(o);
        }
    }
    
    public boolean remove(E e)
    {
        return rootNode.remove(e);
    }
    
    /**
     * do nothing
     */
    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    public void clear()
    {
        rootNode.clear();
    }
    
    /**
     * @throws IllegalArgumentException
     *             si le point a ajouter n'est pas dans la zone couverte par le QuadTree
     */
    public boolean add(E e)
    {
        return rootNode.add(e);
    }
    
    public boolean addAll(Collection<? extends E> c)
    {
        return rootNode.addAll(c);
    }
    
    /**
     * 
     * @param p1
     * @param p2
     * @return a list of nodes that are totally or partially included in the closed rectangle defined by p1 and p2
     */
    public ArrayList<QuadTreeNodeInterface<E>> getNodesInRectangle(PointInterface p1, PointInterface p2)
    {
        ArrayList<QuadTreeNodeInterface<E>> c = new ArrayList<QuadTreeNodeInterface<E>>();
        rootNode.getNodesOverlappingRectangle(p1, p2, c);
        return c;
    }
    
    public ArrayList<QuadTreeNodeInterface<E>> getAllNodes()
    {
        ArrayList<QuadTreeNodeInterface<E>> c = new ArrayList<QuadTreeNodeInterface<E>>();
        rootNode.getAllNodes(c);
        return c;
    }
    
    public ArrayList<QuadTreeNodeInterface<E>> getAllNodesAtDepth(int depth)
    {
        ArrayList<QuadTreeNodeInterface<E>> c = new ArrayList<QuadTreeNodeInterface<E>>();
        rootNode.getAllNodesAtDepth(c, depth);
        return c;
    }
    
    public ArrayList<QuadTreeNodeInterface<E>> getAllLeafNodes()
    {
        ArrayList<QuadTreeNodeInterface<E>> c = new ArrayList<QuadTreeNodeInterface<E>>();
        rootNode.getAllLeafNodes(c);
        return c;
    }
    
    // public void forceTreeSplit(int depth, Double p1, Double p2)
    // {
    // for (int i = 0; i < depth; ++i)
    // {
    // HashSet<QuadTreeNodeInterface<E>> set = new HashSet<QuadTreeNodeInterface<E>>();
    // rootNode.getNodesInRectangle(p1, p2, set);
    //
    // for (QuadTreeNodeInterface<E> node : set)
    // {
    // if (node.isLeafNode() && depth <= maxNodeDepth && node.getDepth() < depth)
    // {
    // node.split();
    // }
    // }
    // }
    // }
    
//    public boolean split(AbstractQuadTreeNode<E> node)
//    {
//        if (maxNodeDepth > node.getDepth())
//        {
//            node.split();
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
    
    public int getMaxNodeDepth()
    {
        return maxNodeDepth;
    }
    
    public int getMaxElementsPerNode()
    {
        return maxElementsPerNode;
    }
    
    public AbstractQuadTreeNode<E> getRootNode()
    {
        return rootNode;
    }
    
    public QuadTreeNodeInterface<E> getLeafNodeAt(PointInterface p)
    {
        return rootNode.getLeafNodeAt(p);
    }
}
