package net.lab0.tools.quadtree;

import java.util.Collection;

import net.lab0.tools.geom.PointInterface;

/**
 * QuadTree
 * 
 * @author 116
 * 
 */
public class QuadTreeNode<E extends PointInterface>
extends AbstractQuadTreeNode<E>
{
    protected QuadTreeNode(QuadTreeRoot<E> treeRoot, PointInterface p1, PointInterface p2)
    {
        super(treeRoot, p1, p2);
    }
    
    protected QuadTreeNode(AbstractQuadTreeNode<E> parent, int positionInParentNode)
    {
        super(parent, positionInParentNode);
    }
    
    public QuadTreeNode(PointInterface p1, PointInterface p2)
    {
        super(p1, p2);
    }
    
    protected void getElementsOverlappingRectangle(double rectMaxX, double rectMaxY, double rectMinX, double rectMinY, Collection<E> collection)
    {
        // si la zone de cette node est entièrement contenue dans le rectangle
        if (this.minX >= rectMinX && this.maxX <= rectMaxX && this.maxY <= rectMaxY && this.minY >= rectMinY)
        {
            this.getAllElements(collection);
        }
        else if (children == null)
        {
            for (E e : elements)
            {
                if (e.getX() >= rectMinX && e.getX() <= rectMaxX && e.getY() >= rectMinY && e.getY() <= rectMaxY)
                {
                    collection.add(e);
                }
            }
        }
        else
        {
            for (int zone : getZonesOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY))
            {
                children.get(zone).getElementsOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY, collection);
            }
        }
    }
    
    /**
     * @param e
     *            un élément à situer
     * @return la zone dans laquelle se situe l'élément
     */
    protected int getElementZone(E e)
    {
        if (isInbounds(e))
        {
            if (e.getX() >= splitPoint.x)
            {
                if (e.getY() >= splitPoint.y)
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
                if (e.getY() >= splitPoint.y)
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
     * @param e
     * @return <code>true</code> si cet élément est dans cette zone
     */
    protected boolean isInbounds(E e)
    {
        return (e.getX() >= minX && e.getX() < maxX && e.getY() >= minY && e.getY() < maxY);
    }
    
    protected QuadTreeNodeInterface<E> getElementNode(E e)
    {
        if (isInbounds(e))
        {
            if (this.children == null)
            {
                if (this.elements.contains(e))
                {
                    return this;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return children.get(getElementZone(e)).getElementNode(e);
            }
        }
        else
        {
            return null;
        }
    }
    
    public boolean contains(E e)
    {
        if (e == null)
        {
            return false;
        }
        else
        {
            QuadTreeNode<E> node = (QuadTreeNode<E>) getElementNode(e);
            if (node == null)
            {
                return false;
            }
            else
            {
                return node.elements.contains(e);
            }
        }
    }
    
    @Override
    protected boolean canBePushedDown(E e)
    {
        // accepte uniquement des points donc nécessairement vrai
        return true;
    }
    
    @Override
    protected AbstractQuadTreeNode<E> newNode(AbstractQuadTreeNode<E> abstractQuadTreeNode, int zone)
    {
        return new QuadTreeNode<E>(abstractQuadTreeNode, zone);
    }
}
