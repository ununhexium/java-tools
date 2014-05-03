
package net.lab0.tools;


import net.lab0.tools.geom.PointInterface;


public class MyTestPoint
implements PointInterface
{
    private double x;
    private double y;
    private String s;
    
    public MyTestPoint(double x, double y, String s)
    {
        super();
        this.x = x;
        this.y = y;
        this.s = s;
    }
    
    @Override
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    @Override
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
    
    public String getS()
    {
        return s;
    }
    
    public void setS(String s)
    {
        this.s = s;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((s == null) ? 0 : s.hashCode());
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
        MyTestPoint other = (MyTestPoint) obj;
        if (s == null)
        {
            if (other.s != null)
                return false;
        }
        else if (!s.equals(other.s))
            return false;
        return true;
    }
    
    @Override
    public double distanceTo(PointInterface other)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
