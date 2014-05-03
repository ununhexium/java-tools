// $codepro.audit.disable logExceptions

package net.lab0.tools.quadtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import net.lab0.tools.MyTestPoint;
import net.lab0.tools.geom.Point;
import net.lab0.tools.geom.PointInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>QuadTreeRootTest</code> contains tests for the class <code>{@link QuadTreeRoot}</code>.
 * 
 * @generatedBy CodePro at 10.07.11 10:45
 * @author 116
 * @version $Revision: 1.0 $
 */
public class QuadTreeRootTest
{
    protected QuadTreeRoot<PointInterface> tree;
    private Random                         random = new Random(System.currentTimeMillis());
    
    @Test
    public void testQuadTreeRoot_1()
    throws Exception
    {
        int cycles = 100;
        for (int i = 0; i < cycles; ++i)
        {
            int maxElementsPerNode = (int) (Math.random() * cycles + 1);
            int maxNodeDepth = (int) (Math.random() * cycles + 1);
            QuadTreeRoot<PointInterface> result = getQuadTreeRoot(maxElementsPerNode, maxNodeDepth);
            
            assertNotNull(result);
            assertEquals(0, result.size());
            assertEquals(maxElementsPerNode, result.maxElementsPerNode);
            assertEquals(maxNodeDepth, result.maxNodeDepth);
            assertEquals(result.rootNode.getMaxX(), 1.0, 0);
            assertEquals(result.rootNode.getMaxY(), 1.0, 0);
            assertEquals(result.rootNode.getMinX(), -1.0, 0);
            assertEquals(result.rootNode.getMinY(), -1.0, 0);
        }
    }
    
    @Test
    public void testAdd_1()
    throws Exception
    {
        try
        {
            tree.add(null);
            assertTrue(false);
        }
        catch (NullPointerException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
        
        try
        {
            AbstractQuadTreeNode<PointInterface> rootNode = new QuadTreeNode<PointInterface>(new Point(), new Point());
            new QuadTreeRoot<PointInterface>(1, 1, rootNode);
            assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }
    
    @Test
    public void testAdd_2()
    throws Exception
    {
        boolean result;
        
        result = tree.add(new MyTestPoint(0, 0, "Test"));
        assertTrue(result);
        
        try
        {
            result = tree.add(new MyTestPoint(1.0, 1.0, "Test"));
            assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
        
        try
        {
            result = tree.add(new MyTestPoint(1.0, -1.0, "Test"));
            assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
        
        try
        {
            result = tree.add(new MyTestPoint(-1.0, 1.0, "Test"));
            assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
        
        result = tree.add(new MyTestPoint(-1.0, -1.0, "Test"));
        assertTrue(result);
        
        try
        {
            result = tree.add(new MyTestPoint(1.0, 0, "Test"));
            assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
        
        try
        {
            result = tree.add(new MyTestPoint(0, 1.0, "Test"));
            assertTrue(false);
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
        
        result = tree.add(new MyTestPoint(-1.0, 0, "Test"));
        assertTrue(result);
        
        result = tree.add(new MyTestPoint(0, -1.0, "Test"));
        assertTrue(result);
    }
    
    @Test
    public void testAddAll_1()
    throws Exception
    {
        Collection<PointInterface> c = new LinkedList<PointInterface>();
        
        int size = 10000;
        addRandomPoints(size, c);
        
        boolean result = tree.addAll(c);
        assertTrue(result);
        assertEquals(size, tree.size());
    }
    
    @Test
    public void testClear_1()
    throws Exception
    {
        int size = 10000;
        addRandomPoints(size, tree);
        
        assertEquals(size, tree.size());
        tree.clear();
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }
    
    @Test
    public void testContains_1()
    throws Exception
    {
        MyTestPoint o = null;
        
        boolean result = tree.contains(o);
        assertFalse(result);
    }
    
    @Test
    public void testContains_2()
    throws Exception
    {
        MyTestPoint o = new MyTestPoint(0.0, 0.0, "");
        tree.add(o);
        
        boolean result = tree.contains(o);
        assertTrue(result);
    }
    
    @Test
    public void testContains_3()
    throws Exception
    {
        MyTestPoint o = getRandomPoint();
        tree.add(o);
        
        boolean result = tree.contains(o);
        assertTrue(result);
    }
    
    @Test
    public void testContains_4()
    throws Exception
    {
        MyTestPoint o = new MyTestPoint(0.0, 0.0, "");
        
        boolean result = tree.contains(o);
        assertFalse(result);
    }
    
    @Test
    public void testContainsAll_1()
    throws Exception
    {
        Collection<PointInterface> c = new LinkedList<PointInterface>();
        
        addRandomPoints(random.nextInt(1000), c);
        
        addRandomPoints(random.nextInt(10000), tree);
        tree.addAll(c);
        addRandomPoints(random.nextInt(10000), tree);
        
        boolean result = tree.containsAll(c);
        assertTrue(result);
    }
    
    /**
     * Run the Collection<QuadTreeNode<My2DPointInterface>> getAllLeafNodes() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetAllLeafNodes_1()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 200);
        addRandomPoints(200, tree);
        Collection<QuadTreeNodeInterface<PointInterface>> result = tree.getAllLeafNodes();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 200);
        
        int count = 0;
        for (QuadTreeNodeInterface<PointInterface> n : result)
        {
            count += n.size();
            // System.out.println(n);
            assertEquals(null, ((AbstractQuadTreeNode<PointInterface>) n).getChildren());
        }
        assertEquals(200, count);
        
        tree = getQuadTreeRoot(1, 8);
        fillCenterPoints(tree);
        result = tree.getAllLeafNodes();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals((1 << 8) * (1 << 8), result.size());
    }
    
    /**
     * Run the ArrayList<QuadTreeNode<My2DPointInterface>> getAllNodes() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetAllNodes_1()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 8);
        fillCenterPoints(tree);
        ArrayList<QuadTreeNodeInterface<PointInterface>> result = tree.getAllNodes();
        
        assertNotNull(result);
        assertFalse(result.isEmpty());
        int total = 0;
        for (int i = 0; i < 9; ++i)
        {
            total += (1 << i) * (1 << i);
        }
        assertEquals(total, result.size());
        
        assertNotNull(result);
    }
    
    /**
     * Run the ArrayList<My2DPointInterface> getElementsInRectangle(Double,Double) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetElementsInRectangle_1()
    throws Exception
    {
        Point p1 = new Point(0.0, 1.0);
        Point p2 = new Point(1.0, -1.0);
        
        ArrayList<PointInterface> c = new ArrayList<PointInterface>();
        
        int depth = 4;
        tree = getQuadTreeRoot(1, depth);
        fillCenterPoints(tree);
        fillCenterPoints(tree, c, p1, p2);
        
        ArrayList<PointInterface> result = tree.getElementsInRectangle(p1, p2);
        assertNotNull(result);
        assertEquals((1 << depth) * (1 << depth) / 2, c.size());
        assertEquals((1 << depth) * (1 << depth) / 2, result.size());
        assertTrue(result.containsAll(c));
        assertTrue(c.containsAll(result));
    }
    
    /**
     * Run the int getMaxElementsPerNode() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetMaxElementsPerNode_1()
    throws Exception
    {
        int result = tree.getMaxElementsPerNode();
        assertEquals(4, result);
    }
    
    /**
     * Run the int getMaxNodeDepth() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetMaxNodeDepth_1()
    throws Exception
    {
        int result = tree.getMaxNodeDepth();
        assertEquals(8, result);
    }
    
    /**
     * Run the ArrayList<QuadTreeNode<My2DPointInterface>> getNodesInRectangle(Double,Double) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetNodesInRectangle_1()
    throws Exception
    {
        Point p1 = new Point(0.0, 1.0);
        Point p2 = new Point(1.0, -1.0);
        
        for (int depth = 2; depth < 6; depth++)
        {
            tree = getQuadTreeRoot(1, depth);
            fillCenterPoints(tree);
            
            Collection<QuadTreeNodeInterface<PointInterface>> result = tree.getNodesInRectangle(p1, p2);
            assertNotNull(result);
            
            // List<QuadTreeNode<QuadTreeElementInterface, Void>> list = new LinkedList<QuadTreeNode<QuadTreeElementInterface, Void>>(result);
            // Collections.sort(list, new Comparator<QuadTreeNode<QuadTreeElementInterface, Void>>()
            // {
            // @Override
            // public int compare(QuadTreeNode<QuadTreeElementInterface, Void> o1, QuadTreeNode<QuadTreeElementInterface, Void> o2)
            // {
            // return o1.toString().compareTo(o2.toString());
            // }
            // });
            int total = 1;
            for (int i = 0; i < depth + 1; ++i)
            {
                total += (1 << i) * (1 << i) / 2;
            }
            
            // System.out.println("-----------------  depth = " + depth + "  --------------------");
            // for (QuadTreeNode<QuadTreeElementInterface, Void> node : list)
            // {
            // System.out.println(node);
            // }
            // System.out.println("total = " + total);
            // System.out.println();
            assertEquals(total, result.size());
        }
        // System.err.println("next test");
    }
    
    /**
     * Run the ArrayList<QuadTreeNode<My2DPointInterface>> getNodesInRectangle(Double,Double) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testGetNodesInRectangle_2()
    throws Exception
    {
        Point p1 = new Point(0.0, 1.0);
        Point p2 = new Point(-1.0, -1.0);
        
        for (int depth = 2; depth < 6; depth++)
        {
            tree = getQuadTreeRoot(1, depth);
            fillCenterPoints(tree);
            
            Collection<QuadTreeNodeInterface<PointInterface>> result = tree.getNodesInRectangle(p1, p2);
            assertNotNull(result);
            
            // List<QuadTreeNode<QuadTreeElementInterface, Void>> list = new LinkedList<QuadTreeNode<QuadTreeElementInterface, Void>>(result);
            // Collections.sort(list, new Comparator<QuadTreeNode<QuadTreeElementInterface, Void>>()
            // {
            // @Override
            // public int compare(QuadTreeNode<QuadTreeElementInterface, Void> o1, QuadTreeNode<QuadTreeElementInterface, Void> o2)
            // {
            // return o1.toString().compareTo(o2.toString());
            // }
            // });
            
            int total = 1;
            for (int i = 1; i < depth + 1; ++i)
            {
                int add = (1 << i) * (1 << i) / 2 + (1 << i);
                total += add;
                // System.out.println("" + i + " " + add);
            }
            
            // list = new LinkedList<QuadTreeNode<QuadTreeElementInterface, Void>>(result);
            // Collections.sort(list, new Comparator<QuadTreeNode<QuadTreeElementInterface, Void>>()
            // {
            // @Override
            // public int compare(QuadTreeNode<QuadTreeElementInterface, Void> o1, QuadTreeNode<QuadTreeElementInterface, Void> o2)
            // {
            // return o1.toString().compareTo(o2.toString());
            // }
            // });
            // System.out.println("-----------------  depth = " + depth + "  --------------------");
            // for (QuadTreeNode<QuadTreeElementInterface, Void> node : list)
            // {
            // System.out.println(node);
            // }
            // System.out.println("total = " + total);
            // System.out.println();
            assertEquals(total, result.size());
        }
        System.err.println("next test");
    }
    
    /**
     * Run the boolean isEmpty() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testIsEmpty_1()
    throws Exception
    {
        boolean result = tree.isEmpty();
        assertTrue(result);
        
        MyTestPoint p = new MyTestPoint(0.0, 0.0, "");
        tree.add(p);
        result = tree.isEmpty();
        assertFalse(result);
        
        tree.remove(p);
        result = tree.isEmpty();
        assertTrue(result);
        
        addRandomPoints(1000, tree);
        result = tree.isEmpty();
        assertFalse(result);
        
        tree.clear();
        result = tree.isEmpty();
        assertTrue(result);
    }
    
    /**
     * Run the Iterator<My2DPointInterface> iterator() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testIterator_1()
    throws Exception
    {
        Iterator<PointInterface> result = tree.iterator();
        assertNotNull(result);
    }
    
    /**
     * Run the Iterator<My2DPointInterface> iterator() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testIterator_2()
    throws Exception
    {
        addRandomPoints(10000, tree);
        Iterator<PointInterface> result = tree.iterator();
        assertNotNull(result);
        
        int counter = 0;
        while (result.hasNext())
        {
            counter++;
            result.next();
        }
        assertEquals(10000, counter);
    }
    
    /**
     * Run the boolean remove(Object) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testRemove_1()
    throws Exception
    {
        MyTestPoint o = null;
        
        boolean result = tree.remove(o);
        
        assertFalse(result);
    }
    
    /**
     * Run the boolean remove(Object) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testRemove_2()
    throws Exception
    {
        MyTestPoint o = new MyTestPoint(0.0, 0.0, "");
        
        boolean result = tree.remove(o);
        assertFalse(result);
        result = tree.add(o);
        assertTrue(result);
        result = tree.remove(o);
        assertTrue(result);
    }
    
    /**
     * Run the boolean removeAll(Collection<?>) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testRemoveAll_1()
    throws Exception
    {
        Collection<PointInterface> c1 = new LinkedList<PointInterface>();
        Collection<PointInterface> c2 = new LinkedList<PointInterface>();
        
        tree = getQuadTreeRoot(1, 4);
        
        Point p1 = new Point(0.0, 1.0);
        Point p2 = new Point(1.0, -1.0);
        
        Point p3 = new Point(0.0, 1.0);
        Point p4 = new Point(-1.0, -1.0);
        
        fillCenterPoints(tree);
        fillCenterPoints(tree, c1, p1, p2);
        fillCenterPoints(tree, c2, p3, p4);
        
        boolean result = tree.removeAll(c1);
        assertTrue(result);
        assertTrue(tree.containsAll(c2));
    }
    
    /**
     * Run the boolean retainAll(Collection<?>) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testRetainAll_1()
    throws Exception
    {
        tree.retainAll(null);
    }
    
    @Test
    public void testSize_1()
    throws Exception
    {
        int result = tree.size();
        assertEquals(0, result);
        addRandomPoints(10000, tree);
        assertEquals(10000, tree.size());
    }
    
    /**
     * Run the Object[] toArray() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testToArray_1()
    throws Exception
    {
        Object[] result = tree.toArray();
        assertNotNull(result);
        assertEquals(0, result.length);
    }
    
    /**
     * Run the Object[] toArray() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testToArray_2()
    throws Exception
    {
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(-1.0, -1.0);
        
        Collection<PointInterface> c = new HashSet<PointInterface>();
        fillCenterPoints(tree, c, p1, p2);
        
        tree.addAll(c);
        
        Object[] result = tree.toArray();
        assertNotNull(result);
        int length = 0;
        while (length < result.length && result[length] != null)
        {
            length++;
        }
        assertEquals(c.size(), length);
    }
    
    /**
     * Run the Object[] toArray(T[]) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Test
    public void testToArray_3()
    throws Exception
    {
        MyTestPoint[] result = tree.toArray(new MyTestPoint[] {});
        
        assertNotNull(result);
        assertEquals(0, result.length);
    }
    
    @Test
    public void testGetRootNode()
    throws Exception
    {
        tree.toArray(new MyTestPoint[] {});
        assertEquals(tree.rootNode, tree.getRootNode());
    }
    
    /**
     * Perform pre-test initialization.
     * 
     * @throws Exception
     *             if the initialization fails for some reason
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @Before
    public void setUp()
    throws Exception
    {
        tree = getQuadTreeRoot(4, 8);
        assertNotNull(tree);
    }
    
    QuadTreeRoot<PointInterface> getQuadTreeRoot(int maxElementsPerNode, int maxNodeDepth)
    {
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(-1.0, -1.0);
        
        AbstractQuadTreeNode<PointInterface> rootNode = new QuadTreeNode<PointInterface>(p1, p2);
        return new QuadTreeRoot<PointInterface>(maxElementsPerNode, maxNodeDepth, rootNode);
    }
    
    public void addRandomPoints(int count, QuadTreeRoot<PointInterface> root)
    {
        int seed = random.nextInt();
        for (int i = 0; i < count; ++i)
        {
            root.add(new MyTestPoint(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0, "" + (i ^ seed)));
        }
    }
    
    public void addRandomPoints(int count, Collection<PointInterface> c)
    {
        for (int i = 0; i < count; ++i)
        {
            c.add(new MyTestPoint(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0, "Test"));
        }
    }
    
    public void fillCenterPoints(QuadTreeRoot<PointInterface> root)
    {
        int depth = root.getMaxNodeDepth();
        
        double xStep = ((root.rootNode.getMaxX() - root.rootNode.getMinX()) / (1 << depth));
        double xOffset = xStep / 2;
        double yStep = ((root.rootNode.getMaxY() - root.rootNode.getMinY()) / (1 << depth));
        double yOffset = yStep / 2;
        
        // System.out.println(depth);
        // System.out.println(xStep);
        // System.out.println(yStep);
        
        for (int i = 0; i < 1 << depth; ++i)
        {
            for (int j = 0; j < 1 << depth; ++j)
            {
                // System.out.println((root.rootNode.getMinX() + xOffset + xStep * i) + " / " + (root.rootNode.getMinY() + yOffset + yStep * j));
                double x = root.rootNode.getMinX() + xOffset + xStep * i;
                double y = root.rootNode.getMinY() + yOffset + yStep * j;
                // System.out.println((root.rootNode.getMinX() + xOffset + xStep * i) + " / " + (root.rootNode.getMinY() + yOffset + yStep * j));
                root.add(new MyTestPoint(x, y, "" + i + "x" + j));
            }
        }
    }
    
    public void fillCenterPoints(QuadTreeRoot<PointInterface> root, Collection<PointInterface> c, Point p1, Point p2)
    {
        int depth = root.getMaxNodeDepth();
        
        double xStep = ((root.rootNode.getMaxX() - root.rootNode.getMinX()) / (1 << depth));
        double xOffset = xStep / 2;
        double yStep = ((root.rootNode.getMaxY() - root.rootNode.getMinY()) / (1 << depth));
        double yOffset = yStep / 2;
        
        for (int i = 0; i < 1 << depth; ++i)
        {
            for (int j = 0; j < 1 << depth; ++j)
            {
                double x = root.rootNode.getMinX() + xOffset + xStep * i;
                double y = root.rootNode.getMinY() + yOffset + yStep * j;
                if (insideRectangle(x, y, p1, p2))
                {
                    // System.out.println((root.rootNode.getMinX() + xOffset + xStep * i) + " / " + (root.rootNode.getMinY() + yOffset + yStep * j));
                    c.add(new MyTestPoint(x, y, "" + i + "x" + j));
                }
            }
        }
    }
    
    public boolean insideRectangle(double x, double y, Point p1, Point p2)
    {
        return (x <= Math.max(p1.x, p2.x) && x >= Math.min(p1.x, p2.x) && y <= Math.max(p1.y, p2.y) && y >= Math.min(p1.y, p2.y));
    }
    
    public MyTestPoint getRandomPoint()
    {
        return new MyTestPoint(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0, "Test");
    }
    
    public MyTestPoint getRandomPoint(String value)
    {
        return new MyTestPoint(Math.random() * 2.0 - 1.0, Math.random() * 2.0 - 1.0, value);
    }
    
    /**
     * Perform post-test clean-up.
     * 
     * @throws Exception
     *             if the clean-up fails for some reason
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    @After
    public void tearDown()
    throws Exception
    {
        // Add additional tear down code here
    }
    
    /**
     * Launch the test.
     * 
     * @param args
     *            the command line arguments
     * 
     * @generatedBy CodePro at 10.07.11 10:45
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(QuadTreeRootTest.class);
    }
}
