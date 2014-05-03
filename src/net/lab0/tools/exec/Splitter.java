package net.lab0.tools.exec;

/**
 * A cascading job that outputs multiple elements from a single element.
 * 
 * @author 116
 * 
 * @param <In>
 * @param <Out>
 */
public abstract class Splitter<In, Out>
extends CascadingJob<In, Out>
{
    public Splitter(CascadingJob<?, In> parent, JobBuilder<Out> jobBuilder)
    {
        super(parent, jobBuilder);
    }
    
    @Override
    public final void executeTask()
    throws Exception
    {
        if (hasNext())
        {
            Out output = nextStep();
            if (output != null)
            {
                addJob(output);
            }
            addJob(this);
        }
    }
    
    /**
     * This method implements the several steps that have to be performed.
     * 
     * @return Data of type <code>Out</code>. <code>null</code> is permitted if it is the only way to return something
     *         from the method.
     * @throws Exception
     */
    public abstract Out nextStep()
    throws Exception;
    
    /**
     * @return <code>true</code> if there is a next step.
     */
    public abstract boolean hasNext();
}
