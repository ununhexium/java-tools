package net.lab0.tools.exec;

public abstract class Generator<Source, Out>
extends CascadingJob<Void, Out>
{
    private Source source;
    
    public Generator(PriorityExecutor executor, JobBuilder<Out> jobBuilder, Source source)
    {
        super(executor, 0, jobBuilder);
        this.source = source;
    }
    
    @Override
    public final void executeTask()
    throws Exception
    {
        Out output = null;
        if ((output = generate(source)) != null)
        {
            addJob(output);
            addJob(this);
        }
    }
    
    /**
     * This method implements the several steps that have to be performed.
     * 
     * @return <code>Out</code> or <code>null</code> to indicate the last step of this job.
     * @throws Exception
     */
    public abstract Out generate(Source source)
    throws Exception;
}
