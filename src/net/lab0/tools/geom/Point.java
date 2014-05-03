package net.lab0.tools.geom;

import java.awt.geom.Point2D;

public class Point
extends Point2D.Double
implements PointInterface
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8776530175162884459L;
    
    public Point(double x, double y)
    {
        super(x, y);
    }
    
    public Point()
    {
        super(0, 0);
    }
    
    public Point(Point other)
    {
        super(other.x, other.y);
    }
    
    public Point(PointInterface other)
    {
        super(other.getX(), other.getY());
    }
    
    @Override
    public double distanceTo(PointInterface other)
    {
        double xDiff = x - other.getX();
        double yDiff = y - other.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    
    @Override
    public String toString()
    {
        return "Point [x=" + x + ", y=" + y + "]";
    }
    
    /**
     * Converts a string formatted as <code>x, y</code> to a point instance. The values must be convertible to
     * {@link Double} with <code>Double.valueOf()</code>. The space after the <code>,</code> is optional.
     * 
     * @param string
     *            A formated string
     * @return a point with the given coordinates.
     */
    public static Point valueOf(String string)
    {
        String[] strings = string.split(",");
        if (strings.length != 2)
        {
            throw new IllegalArgumentException("Error while trying to convert " + string
            + " to a point. The number of comas (expected:1) is invalid");
        }
        
        double x = java.lang.Double.valueOf(strings[0]);
        double y = java.lang.Double.valueOf(strings[1]);
        
        return new Point(x, y);
    }
}
