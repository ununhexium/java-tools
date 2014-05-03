package net.lab0.tools.quadtree;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;

import net.lab0.tools.geom.PointInterface;

public interface QuadTreeNodeInterface<E extends PointInterface>
extends Iterable<E>
{
    public static final int   NW                 = 0;
    public static final int   NE                 = 1;
    public static final int   SW                 = 2;
    public static final int   SE                 = 3;
    
    public static final int[] zones              = { NW, NE, SW, SE };
    
    // utilisé pour indiquer que quelque chose se situe en dehors de la node courrante
    public static final int   OUTSIDE            = -1;
    public static final int   SELF               = 4;
    public static final int   ROOT               = 5;
    
    // informations utilisées pour la navigation dans le QuadTree
    
    // types des ancetres communs
    public static final int[] rightAncestors     = new int[] { NW, SW };
    public static final int[] leftAncestors      = new int[] { NE, SE };
    public static final int[] bottomAncestors    = new int[] { NW, NE };
    public static final int[] topAncestors       = new int[] { SW, SE };
    
    // tableau de symetrie pour l'inversion des chemins
    public static final int[] horizontalReverter = new int[] { NE, NW, SE, SW };
    public static final int[] verticalReverter   = new int[] { SW, SE, NW, NE };
    
    // types des enfants
    public static final int[] leftNodes          = new int[] { NW, SW };
    public static final int[] rightNodes         = new int[] { NE, SE };
    public static final int[] topNodes           = new int[] { NW, NE };
    public static final int[] bottomNodes        = new int[] { SW, SE };
    
    public String getPath();
    
    public String getCompactPath();
    
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
    
    public QuadTreeRoot<E> getTreeRoot();
    
    public int getPositionInParentNode();
    
    public QuadTreeNodeInterface<E> getParent();
    
    public int getDepth();
    
    public Point2D.Double getSplitPoint();
    
    /**
     * exclus de la zone de cette node
     * 
     * @return
     */
    public double getMaxX();
    
    /**
     * exclus de la zone de cette node
     * 
     * @return
     */
    public double getMaxY();
    
    /**
     * inclus dans la zone de cette node
     * 
     * @return
     */
    public double getMinX();
    
    /**
     * inclus dans la zone de cette node
     * 
     * @return
     */
    public double getMinY();
    
    public boolean add(E e);
    
    public boolean contains(QuadTreeNodeInterface<E> other);
    
    public boolean addAll(Collection<? extends E> elementsToAdd);
    
    public String toString();
    
    public boolean remove(E e);
    
    public boolean isEmpty();
    
    public void clear();
    
    public List<QuadTreeNodeInterface<E>> getLeftChildren();
    
    public List<QuadTreeNodeInterface<E>> getRightChildren();
    
    public List<QuadTreeNodeInterface<E>> getBottomChildren();
    
    public List<QuadTreeNodeInterface<E>> getTopChildren();
    
    public List<QuadTreeNodeInterface<E>> getRightNeighbors();
    
    public List<QuadTreeNodeInterface<E>> getLeftNeighbors();
    
    public List<QuadTreeNodeInterface<E>> getTopNeighbors();
    
    public List<QuadTreeNodeInterface<E>> getBottomNeighbors();
    
    public int getNodesCount();
    
    public int size();
    
    public boolean isLeafNode();
}
