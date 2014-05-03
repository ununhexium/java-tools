package net.lab0.tools.quadtree;

import net.lab0.tools.geom.PointInterface;

public class DataQuadTreeNode<E extends PointInterface, N> extends QuadTreeNode<E>
{
    /**
     * L'objet attaché à cette node
     */
    private N nodeElement;
    
    public DataQuadTreeNode(QuadTreeNode<E> parent, int positionInParentNode)
    {
        super(parent, positionInParentNode);
    }
    
    public DataQuadTreeNode(QuadTreeRoot<E> treeRoot, PointInterface p1, PointInterface p2)
    {
        super(treeRoot, p1, p2);
    }
    
    public DataQuadTreeNode(PointInterface p1, PointInterface p2)
    {
        super(p1, p2);
    }
    
    public N getNodeElement()
    {
        return nodeElement;
    }
    
    public void setNodeElement(N nodeElement)
    {
        this.nodeElement = nodeElement;
    }
    
    public boolean isEmpty()
    {
        return nodeElement == null && super.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public DataQuadTreeNode<E, N> getNode(int position)
    {
        if (position >= 0 && position <= 3)
        {
            if (children != null)
            {
                return (DataQuadTreeNode<E, N>) children.get(position);
            }
        }
        return null;
    }
}
