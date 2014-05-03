package net.lab0.tools.quadtree;


import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.lab0.tools.geom.PointInterface;


public class QuadTreeIterator<E extends PointInterface>
implements Iterator<E>
{
	private boolean						hasNext	= true;
	private Collection<QuadTreeNodeInterface<E>>	nodes;
	private Iterator<QuadTreeNodeInterface<E>>	nodesIterator;
	private Iterator<E>					elementsIterator;
	private Iterator<E>					removeIterator;
	
	public QuadTreeIterator(QuadTreeRoot<E> quadTree)
	{
		nodes = quadTree.getAllLeafNodes();
//		System.out.println("nodes : " + nodes.size());
		nodesIterator = nodes.iterator();
		if (nodesIterator.hasNext())
		{
		    removeIterator = nodesIterator.next().iterator();
			elementsIterator = removeIterator;
			
			findNext();
		}
		else
		{
//			System.out.println("no next");
			hasNext = false;
		}
	}
	
	private void findNext()
	{
//		System.out.println("find next");
		if (hasNext)
		{
			if (elementsIterator.hasNext())
			{
//				System.out.println("elementsIterator.hasNext()");
				return;
			}
			else
			{
				removeIterator = elementsIterator;
				while (nodesIterator.hasNext())
				{
					elementsIterator = nodesIterator.next().iterator();
					if (elementsIterator.hasNext())
					{
//						System.out.println("found next");
						return;
					}
				}
			}
			
			hasNext = false;
		}
	}
	
	@Override
	public boolean hasNext()
	{
		return hasNext;
	}
	
	@Override
	public E next()
	{
//		System.out.println("next");
		if (hasNext)
		{
			E e = elementsIterator.next();
//			System.out.println("next");
			findNext();
			return e;
		}
		else
		{
			throw new NoSuchElementException();
		}
	}
	
	@Override
	public void remove()
	{
		removeIterator.remove();
	}
	
}
