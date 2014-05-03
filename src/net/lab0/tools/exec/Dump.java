package net.lab0.tools.exec;

import java.util.List;

/**
 * Class for test/debug. dumps the input data into the given list. Run with the priority = Integer.MAX_VALUE - 64
 * 
 * @author 116
 * 
 */
public class Dump<In>
extends CascadingJob<In, Void>
{
    private List<In> dumpList;
    private In       element;
    
    public Dump(PriorityExecutor executor, In element, List<In> dumpList)
    {
        super(executor, Integer.MAX_VALUE - 64, null);
        this.dumpList = dumpList;
        this.element = element;
    }
    
    @Override
    public final void executeTask()
    {
        /*
         * This is a bit of an overkill: doing it in the constructor has the same effect. Keeping it here for
         * consistency.
         */
        dumpList.add(element);
    }
}
