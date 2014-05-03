
package net.lab0.tools.geom;


public interface RectangleInterface
{
    
    /**
     * equivalent to {@code getMaxY()}
     * 
     * @return
     */
    double getTop();
    
    /**
     * equivalent to {@code getMinY()}
     * 
     * @return
     */
    double getBottom();
    
    /**
     * equivalent to {@code getMinX()}
     * 
     * @return
     */
    double getLeft();
    
    /**
     * equivalent to {@code getMaxX()}
     * 
     * @return
     */
    double getRight();
    
    double getMinX();
    
    double getMinY();
    
    double getMaxX();
    
    double getMaxY();
    
    double getWidth();
    
    double getHeight();
    
    PointInterface getTopLeft();
    
    PointInterface getTopRight();
    
    PointInterface getBottomLeft();
    
    PointInterface getBottomRight();
    
    PointInterface getCenter();
    
    /**
     * 
     * @param other
     * @return <code>true</code> if the other rectangle overlaps this one
     */
    public boolean overlap(RectangleInterface other);
}
