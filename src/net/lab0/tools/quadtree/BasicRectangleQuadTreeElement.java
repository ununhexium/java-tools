
package net.lab0.tools.quadtree;


import net.lab0.tools.geom.PointInterface;
import net.lab0.tools.geom.Rectangle;
import net.lab0.tools.geom.RectangleInterface;


public class BasicRectangleQuadTreeElement
extends Rectangle
implements QuadTreeRectangleElementInterface
{
    
    public BasicRectangleQuadTreeElement(PointInterface a, PointInterface b)
    {
        super(a, b);
    }
    
    public BasicRectangleQuadTreeElement(Rectangle other)
    {
        super(other);
    }
    
    public BasicRectangleQuadTreeElement(RectangleInterface other)
    {
        super(other);
    }
    
    @Override
    public double getX()
    {
        return getCenter().getX();
    }
    
    @Override
    public double getY()
    {
        return getCenter().getY();
    }
    
    @Override
    public double distanceTo(PointInterface other)
    {
        double xDiff = getX() - other.getX();
        double yDiff = getY() - other.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
