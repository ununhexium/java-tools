package net.lab0.tools.quadtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.lab0.tools.MyTestPoint;
import net.lab0.tools.geom.Point;
import net.lab0.tools.geom.PointInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>QuadTreeNodeTest</code> contains tests for the class <code>{@link QuadTreeNode}</code>.
 * 
 * @generatedBy CodePro at 16.07.11 16:27
 * @author 116
 * @version $Revision: 1.0 $
 */
public class QuadTreeNodeTest
{
    protected QuadTreeRoot<PointInterface> tree;
    
    /**
     * Run the void forceTreeSplit(int) method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testForceTreeSplit_1()
    throws Exception
    {
        int MAX = 6;
        tree = getQuadTreeRoot(1, MAX);
        for (int depth = 0; depth <= MAX; ++depth)
        {
            tree.getRootNode().forceTreeSplit(depth);
            int total = 0;
            for (int i = 0; i <= depth; ++i)
            {
                total += (1 << i) * (1 << i);
            }
            assertEquals(total, tree.getAllNodes().size());
            assertEquals((1 << depth) * (1 << depth), tree.getAllLeafNodes().size());
        }
        
        for (int depth = MAX + 1; depth <= MAX + 3; ++depth)
        {
            tree.getRootNode().forceTreeSplit(depth);
            int total = 0;
            for (int i = 0; i <= MAX; ++i)
            {
                total += (1 << i) * (1 << i);
            }
            assertEquals(total, tree.getAllNodes().size());
            assertEquals((1 << MAX) * (1 << MAX), tree.getAllLeafNodes().size());
        }
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getBottomChildren() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetBottomChildren_1()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        List<String> nodes = Arrays.asList("5.2.2.2", "5.2.2.3", "5.2.3.2", "5.2.3.3");
        
        List<QuadTreeNodeInterface<PointInterface>> result = tree.getRootNode().getChildren().get(2).getBottomChildren();
        
        assertNotNull(result);
        
        sortListByNodeName(result);
        List<String> resultList = new ArrayList<String>();
        
        System.out.println("testGetBottomChildren_1()");
        for (QuadTreeNodeInterface<PointInterface> node : result)
        {
            System.out.println(node.getPath());
            resultList.add(node.getPath());
        }
        System.out.println();
        
        assertTrue(nodes.containsAll(resultList));
        assertTrue(resultList.containsAll(nodes));
    }
    
    @Test
    public void testGetBottomChildren_2()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        List<String> nodes = Arrays.asList("5.1.2.2", "5.1.2.3", "5.1.3.2", "5.1.3.3");
        
        List<QuadTreeNodeInterface<PointInterface>> result = tree.getRootNode().getChildren().get(1).getBottomChildren();
        sortListByNodeName(result);
        List<String> resultList = new ArrayList<String>();
        
        for (QuadTreeNodeInterface<PointInterface> node : result)
        {
            System.out.println(node.getPath());
            resultList.add(node.getPath());
        }
        System.out.println();
        
        assertNotNull(result);
        assertTrue(nodes.containsAll(resultList));
        assertTrue(resultList.containsAll(nodes));
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getBottomNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetBottomNeighbors_1()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        List<String> nodes = Arrays.asList("5.3.0.0", "5.3.0.1", "5.3.1.0", "5.3.1.1");
        
        List<QuadTreeNodeInterface<PointInterface>> result = tree.getRootNode().getChildren().get(1).getBottomNeighbors();
        sortListByNodeName(result);
        List<String> resultList = new ArrayList<String>();
        
        for (QuadTreeNodeInterface<PointInterface> node : result)
        {
            System.out.println(node.getPath());
            resultList.add(node.getPath());
        }
        System.out.println();
        
        assertNotNull(result);
        assertTrue(nodes.containsAll(resultList));
        assertTrue(resultList.containsAll(nodes));
        
        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        // java.lang.IllegalArgumentException: Points can't be the same
        // at net.lab0.tools.QuadTreeRoot.<init>(QuadTreeRoot.java:39)
        assertNotNull(result);
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getBottomNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetBottomNeighbors_2()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        List<String> nodes = Arrays.asList("5.0.3.0", "5.0.3.1");
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<QuadTreeNodeInterface<PointInterface>> result = ((AbstractQuadTreeNode) ((AbstractQuadTreeNode) tree.getRootNode().getChildren().get(0))
        .getChildren().get(1)).getBottomNeighbors();
        sortListByNodeName(result);
        List<String> resultList = new ArrayList<String>();
        
        for (QuadTreeNodeInterface<PointInterface> node : result)
        {
            System.out.println(node.getPath());
            resultList.add(node.getPath());
        }
        System.out.println();
        
        assertNotNull(result);
        assertTrue(nodes.containsAll(resultList));
        assertTrue(resultList.containsAll(nodes));
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getBottomNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetBottomNeighbors_3()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        List<QuadTreeNodeInterface<PointInterface>> result = tree.getRootNode().getBottomNeighbors();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        result = tree.getRootNode().getChildren().get(2).getBottomNeighbors();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    /**
     * Run the QuadTreeNode<QuadTreeElementInterface, Void> getBottomSibling() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testGetBottomSibling_1()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        QuadTreeNodeInterface<PointInterface> result = ((AbstractQuadTreeNode) ((AbstractQuadTreeNode) tree.getRootNode().getChildren().get(0)).getChildren()
        .get(1)).getBottomSibling();
        
        System.out.println(result.getPath());
        System.out.println();
        
        assertNotNull(result);
        assertEquals("5.0.3", result.getPath());
        
        result = tree.getRootNode().getBottomSibling();
        assertNull(result);
        
        result = ((AbstractQuadTreeNode) tree.getRootNode().getChildren().get(3)).getBottomSibling();
        assertNull(result);
        
        result = ((AbstractQuadTreeNode) tree.getRootNode().getChildren().get(1)).getBottomSibling();
        
        System.out.println(result.getPath());
        System.out.println();
        
        assertNotNull(result);
        assertEquals("5.3", result.getPath());
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getLeftChildren() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetLeftChildren_1()
    throws Exception
    {
        tree = getQuadTreeRoot(1, 3);
        fillCenterPoints(tree);
        
        List<String> nodes = Arrays.asList("5.2.0.0", "5.2.0.2", "5.2.2.0", "5.2.2.2");
        
        List<QuadTreeNodeInterface<PointInterface>> result = tree.getRootNode().getChildren().get(2).getLeftChildren();
        sortListByNodeName(result);
        List<String> resultList = new ArrayList<String>();
        
        for (QuadTreeNodeInterface<PointInterface> node : result)
        {
            System.out.println(node.getPath());
            resultList.add(node.getPath());
        }
        System.out.println();
        
        assertNotNull(result);
        assertTrue(nodes.containsAll(resultList));
        assertTrue(resultList.containsAll(nodes));
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getLeftNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetLeftNeighbors_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getLeftNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetLeftNeighbors_2()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the QuadTreeNode<QuadTreeElementInterface, Void> getLeftSibling() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetLeftSibling_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getRightChildren() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetRightChildren_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getRightNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetRightNeighbors_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getRightNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetRightNeighbors_2()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the QuadTreeNode<QuadTreeElementInterface, Void> getRightSibling() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetRightSibling_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getTopChildren() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetTopChildren_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getTopNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetTopNeighbors_1()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the List<QuadTreeNode<QuadTreeElementInterface, Void>> getTopNeighbors() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetTopNeighbors_2()
    throws Exception
    {
        // should work too
    }
    
    /**
     * Run the QuadTreeNode<QuadTreeElementInterface, Void> getTopSibling() method test.
     * 
     * @throws Exception
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Test
    public void testGetTopSibling_1()
    throws Exception
    {
        // should work too
    }
    
    private void sortListByNodeName(List<QuadTreeNodeInterface<PointInterface>> result)
    {
        Collections.sort(result, new Comparator<QuadTreeNodeInterface<PointInterface>>()
        {
            @Override
            public int compare(QuadTreeNodeInterface<PointInterface> o1, QuadTreeNodeInterface<PointInterface> o2)
            {
                return o1.toString().compareTo(o2.toString());
            }
        });
    }
    
    /**
     * Perform pre-test initialization.
     * 
     * @throws Exception
     *             if the initialization fails for some reason
     * 
     * @generatedBy CodePro at 16.07.11 16:27
     */
    @Before
    public void setUp()
    throws Exception
    {
        tree = getQuadTreeRoot(4, 8);
    }
    
    QuadTreeRoot<PointInterface> getQuadTreeRoot(int maxElementsPerNode, int maxNodeDepth)
    {
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(-1.0, -1.0);
        AbstractQuadTreeNode<PointInterface> rootNode = new QuadTreeNode<PointInterface>(p1, p2);
        
        return new QuadTreeRoot<PointInterface>(maxElementsPerNode, maxNodeDepth, rootNode);
    }
    
    /**
     * Perform post-test clean-up.
     * 
     * @throws Exception
     *             if the clean-up fails for some reason
     * 
     * @generatedBy CodePro at 16.07.11 16:27
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
     * @generatedBy CodePro at 16.07.11 16:27
     */
    public static void main(String[] args)
    {
        new org.junit.runner.JUnitCore().run(QuadTreeNodeTest.class);
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
                // System.out.println((root.rootNode.minX + xOffset + xStep * i) + " / " + (root.rootNode.minY + yOffset + yStep * j));
                double x = root.rootNode.getMinX() + xOffset + xStep * i;
                double y = root.rootNode.getMinY() + yOffset + yStep * j;
                // System.out.println((root.rootNode.minX + xOffset + xStep * i) + " / " + (root.rootNode.minY + yOffset + yStep * j));
                root.add(new MyTestPoint(x, y, "" + i + "x" + j));
            }
        }
    }
    
}
