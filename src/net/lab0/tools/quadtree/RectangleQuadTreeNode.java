package net.lab0.tools.quadtree;

import java.util.Collection;

import net.lab0.tools.geom.PointInterface;

public class RectangleQuadTreeNode<E extends QuadTreeRectangleElementInterface>
extends AbstractQuadTreeNode<E>
{
    public RectangleQuadTreeNode(AbstractQuadTreeNode<E> parent, int positionInParentNode)
    {
        super(parent, positionInParentNode);
    }
    
    public RectangleQuadTreeNode(QuadTreeRoot<E> treeRoot, PointInterface p1, PointInterface p2)
    {
        super(treeRoot, p1, p2);
    }
    
    public RectangleQuadTreeNode(PointInterface p1, PointInterface p2)
    {
        super(p1, p2);
    }
    
    @Override
    protected void getElementsOverlappingRectangle(double rectMaxX, double rectMaxY, double rectMinX, double rectMinY, Collection<E> collection)
    {
        // si la zone de cette node est entièrement contenue dans le rectangle
        if (this.minX >= rectMinX && this.maxX <= rectMaxX && this.maxY <= rectMaxY && this.minY >= rectMinY)
        {
            this.getAllElements(collection);
        }
        else
        {
            for (E e : elements)
            {
                // cet element est partiellement contenu dans le rectangle <=> si le rectangle englobant des ces deux elements n'est pas strictement distinct
                if (!(e.getMinX() > rectMaxX || e.getMaxX() < rectMinX || e.getMinY() > rectMaxY || e.getMaxY() < rectMinY))
                {
                    collection.add(e);
                }
            }
            
            if (children != null)
            {
                for (int zone : getZonesOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY))
                {
                    children.get(zone).getElementsOverlappingRectangle(rectMaxX, rectMaxY, rectMinX, rectMinY, collection);
                }
            }
        }
    }
    
    @Override
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
            int zone = getElementZone(e);
            if (!(zone == SELF || zone == OUTSIDE))
            {
                return children.get(zone).getElementNode(e);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }
    
    @Override
    protected int getElementZone(E e)
    {
        if (isInbounds(e))
        {
            // si le rectangle est a gauche du point de split
            if (e.getMinX() >= splitPoint.x)
            {
                if (e.getMinY() >= splitPoint.y)
                {
                    return NE;
                }
                else if (e.getMaxY() < splitPoint.y)
                {
                    return SE;
                }
            }
            else if (e.getMaxX() < splitPoint.x)
            {
                if (e.getMinY() >= splitPoint.y)
                {
                    return NW;
                }
                else if (e.getMaxY() < splitPoint.y)
                {
                    return SW;
                }
            }
            
            // si le rectangle n'est pas strictement dans une des 4 subdivisions
            return SELF;
        }
        else
        {
            return OUTSIDE;
        }
    }
    
    @Override
    protected boolean canBePushedDown(E e)
    {
        int zone = getElementZone(e);
        return !(zone == SELF/* should not happen --> || zone==OUTSIDE */);
    }
    
    @Override
    protected boolean isInbounds(E e)
    {
        return (e.getMinX() >= minX && e.getMaxX() < maxX && e.getMinY() >= minY && e.getMaxY() < maxY);
    }
    
    @Override
    protected AbstractQuadTreeNode<E> newNode(AbstractQuadTreeNode<E> abstractQuadTreeNode, int zone)
    {
        return new RectangleQuadTreeNode<E>(abstractQuadTreeNode, zone);
    }
    
}
