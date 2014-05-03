package net.lab0.tools.exec;

/**
 * A cascading job that will be executed in 1 step.
 * 
 * @author 116
 * 
 * @param <In>
 * @param <Out>
 */
public abstract class SimpleJob<In, Out>
extends CascadingJob<In, Out>
{
    protected In input;
    
    public SimpleJob(CascadingJob<?, In> parent, JobBuilder<Out> jobBuilder, In input)
    {
        super(parent, jobBuilder);
        this.input = input;
    }
    
    public SimpleJob(PriorityExecutor executor, int priority, JobBuilder<Out> jobBuilder, In input)
    {
        super(executor, priority, jobBuilder);
        this.input = input;
    }
    
    @Override
    public final void executeTask()
    throws Exception
    {
        Out output = singleStep(input);
        addJob(output);
    }
    
    /**
     * Implements the one step action.
     * 
     * @param input
     * @return out
     */
    public abstract Out singleStep(In input);
    
}
