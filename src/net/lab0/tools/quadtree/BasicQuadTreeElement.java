
package net.lab0.tools.quadtree;


import net.lab0.tools.geom.Point;


public class BasicQuadTreeElement
extends Point
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    double                    x;
    double                    y;
    
    public BasicQuadTreeElement(double x, double y)
    {
        super();
        this.x = x;
        this.y = y;
    }
    
    @Override
    public double getX()
    {
        return x;
    }
    
    @Override
    public double getY()
    {
        return y;
    }
    
}
