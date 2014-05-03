package net.lab0.tools.quadtree;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.lab0.tools.geom.Point;
import net.lab0.tools.geom.Rectangle;

import org.junit.Test;

public class RectangleQuadTreeTest
{
    
    @Test
    public void testQuadTreeElementsPosition()
    {
        AbstractQuadTreeNode<BasicRectangleQuadTreeElement> rootNode = new RectangleQuadTreeNode<BasicRectangleQuadTreeElement>(new Point(-1.0, -1.0), new Point(1.0, 1.0));
        QuadTreeRoot<BasicRectangleQuadTreeElement> root = new QuadTreeRoot<BasicRectangleQuadTreeElement>(1, 5, rootNode);
        
        BasicRectangleQuadTreeElement a = new BasicRectangleQuadTreeElement(new Point(-0.25, 0.25), new Point(-0.75, 0.75));
        BasicRectangleQuadTreeElement b = new BasicRectangleQuadTreeElement(new Point(0.25, 0.25), new Point(0.75, 0.75));
        BasicRectangleQuadTreeElement c = new BasicRectangleQuadTreeElement(new Point(0.25, -0.25), new Point(0.75, -0.75));
        BasicRectangleQuadTreeElement d = new BasicRectangleQuadTreeElement(new Point(-0.25, -0.25), new Point(-0.75, -0.75));
        
        BasicRectangleQuadTreeElement e = new BasicRectangleQuadTreeElement(new Point(-0.25, 0.5), new Point(0.25, 0.5));
        BasicRectangleQuadTreeElement f = new BasicRectangleQuadTreeElement(new Point(-0.25, -0.5), new Point(0.25, 0.5));
        BasicRectangleQuadTreeElement g = new BasicRectangleQuadTreeElement(new Point(0.5, 0.25), new Point(0.5, -0.25));
        BasicRectangleQuadTreeElement h = new BasicRectangleQuadTreeElement(new Point(-0.5, 0.25), new Point(-0.5, -0.25));
        
        root.add(a);
        root.add(b);
        root.add(c);
        root.add(d);
        
        root.add(e);
        root.add(f);
        root.add(g);
        root.add(h);
        
        for (int i = 0; i < 2; ++i)
        {
            List<QuadTreeNodeInterface<BasicRectangleQuadTreeElement>> nodes = root.getAllNodesAtDepth(i);
            for (QuadTreeNodeInterface<BasicRectangleQuadTreeElement> node : nodes)
            {
                System.out.print(node.getPath() + " ");
                for (BasicRectangleQuadTreeElement r : node)
                {
                    System.out.print(r.getCenter());
                }
                System.out.println();
            }
        }
        
        Rectangle rect1 = new Rectangle(new Point(-0.9, 0.9), new Point(0.9, 0.1));
        List<BasicRectangleQuadTreeElement> result1 = root.getElementsInRectangle(rect1.getTopLeft(), rect1.getBottomRight());
        
        assertTrue(result1.contains(a));
        assertTrue(result1.contains(b));
        assertTrue(result1.contains(e));
        assertTrue(result1.contains(g));
        assertTrue(result1.contains(h));
        
        Rectangle rect2 = new Rectangle(new Point(-0.9, -0.1), new Point(0.9, -0.9));
        List<BasicRectangleQuadTreeElement> result2 = root.getElementsInRectangle(rect2.getTopLeft(), rect2.getBottomRight());
        
        assertTrue(result2.contains(c));
        assertTrue(result2.contains(d));
        assertTrue(result2.contains(f));
        assertTrue(result2.contains(g));
        assertTrue(result2.contains(h));
    }
    
}
