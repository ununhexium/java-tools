package net.lab0.tools.geom;


public class Rectangle
implements RectangleInterface
{
    protected PointInterface a;
    protected PointInterface b;
    
    public Rectangle(PointInterface a, PointInterface b)
    {
        this.a = a;
        this.b = b;
        Double.valueOf("1.0");
    }
    
    public Rectangle(Rectangle other)
    {
        this(other.a, other.b);
    }
    
    public Rectangle(RectangleInterface other)
    {
        this(other.getBottomLeft(), other.getTopRight());
    }
    
    @Override
    public double getTop()
    {
        return getMaxY();
    }
    
    @Override
    public double getBottom()
    {
        return getMinY();
    }
    
    @Override
    public double getLeft()
    {
        return getMinX();
    }
    
    @Override
    public double getRight()
    {
        return getMaxX();
    }
    
    @Override
    public double getMinX()
    {
        return Math.min(a.getX(), b.getX());
    }
    
    @Override
    public double getMinY()
    {
        return Math.min(a.getY(), b.getY());
    }
    
    @Override
    public double getMaxX()
    {
        return Math.max(a.getX(), b.getX());
    }
    
    @Override
    public double getMaxY()
    {
        return Math.max(a.getY(), b.getY());
    }
    
    @Override
    public PointInterface getTopLeft()
    {
        return new Point(getMinX(), getMaxY());
    }
    
    @Override
    public PointInterface getTopRight()
    {
        return new Point(getMaxX(), getMaxY());
    }
    
    @Override
    public PointInterface getBottomLeft()
    {
        return new Point(getMinX(), getMinY());
    }
    
    @Override
    public PointInterface getBottomRight()
    {
        return new Point(getMaxX(), getMinY());
    }
    
    @Override
    public PointInterface getCenter()
    {
        return new Point((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }
    
    @Override
    public double getWidth()
    {
        return getMaxX() - getMinX();
    }
    
    @Override
    public double getHeight()
    {
        return getMaxY() - getMinY();
    }
    
    @Override
    public String toString()
    {
        return "Rectangle [a=" + a + ", b=" + b + "]";
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((a == null) ? 0 : a.hashCode());
        result = prime * result + ((b == null) ? 0 : b.hashCode());
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
        Rectangle other = (Rectangle) obj;
        if (getBottomLeft().equals(other.getBottomLeft()) && getTopRight().equals(other.getTopRight()))
        {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean overlap(RectangleInterface other)
    {
        return (!(this.getMinX() > other.getMaxX() || this.getMaxX() < other.getMinX()
        || this.getMinY() > other.getMaxY() || this.getMaxY() < other.getMinY()));
    }
    
    /**
     * Converts a string formatted as <code>minX, maxX, minY, maxY</code> to a rectangle instance. The values must be
     * convertible to {@link Double} with <code>Double.valueOf()</code>. The space after the <code>,</code> is optional.
     * 
     * @param string
     *            A formated string
     * @return a rectangle with the given coordinates.
     */
    public static Rectangle valueOf(String string)
    {
        String[] strings = string.split(",");
        if (strings.length != 4)
        {
            throw new IllegalArgumentException("Error while trying to convert " + string
            + " to a rectangle. The number of comas (expected:3) is invalid");
        }
        double[] values = new double[4];
        for (int i = 0; i < 4; ++i)
        {
            values[i] = Double.valueOf(strings[i]);
        }
        
        PointInterface a = new Point(values[0], values[2]);
        PointInterface b = new Point(values[1], values[3]);
        return new Rectangle(a, b);
    }
}
