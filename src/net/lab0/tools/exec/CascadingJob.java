package net.lab0.tools.exec;

/**
 * A prioritized runnable/job, to be used in conjunction with {@link PriorityExecutor} that can call subsequent.
 * 
 * @author 116@lab0.net
 * @param <In> Job's input type
 * @param <Out> Job's output type
 * 
 */
public abstract class CascadingJob<In, Out>
extends DefaultPriorizedRunnable
{
    private final PriorityExecutor executor;
    protected JobBuilder<Out>            jobBuilder;
    
    /**
     * Creates a runnable with the given priority. The subsequent jobs will be executed by the given executor.
     * 
     * @param priority
     *            the priority for this runnable.
     * @param executor
     *            The executor to send the consecutive jobs to. This must be a {@link PriorityExecutor} to take the
     *            priority into account.
     * @param jobBuilder
     *            The builder that will create the next job to be run.
     */
    public CascadingJob(PriorityExecutor executor, int priority, JobBuilder<Out> jobBuilder)
    {
        super(priority);
        this.executor = executor;
        this.jobBuilder = jobBuilder;
    }
    
    /**
     * The priority of the next job will be higher than the one of the parent job. This is to ensure that children jobs
     * will be executed before the parents and avoid overflows due to a lot of child processes that will be executed
     * only when the parent finishes.
     * 
     * @param parent
     *            The parent job to get the executor and priority from.
     * @param jobBuilder
     *            The builder that will create the next job to be run.
     */
    public CascadingJob(CascadingJob<?, In> parent, JobBuilder<Out> jobBuilder)
    {
        this(parent.getExecutor(), parent.getPriority() + 1, jobBuilder);
    }
    
    @Override
    public final void run()
    {
        try
        {
            executeTask();
        }
        catch (Exception e)
        {
            throw new UnhandledException("Unhandled exception recieved", e);
        }
    }
    
    /**
     * The task to execute in this runnable.
     * 
     * @throws Exception 
     */
    public abstract void executeTask()
    throws Exception;
    
    /**
     * @param output The job to add
     */
    public void addJob(Out output)
    {
        executor.execute(jobBuilder.buildJob(this, output));
    }
    
    public void addJob(CascadingJob<?, ?> job)
    {
        executor.execute(job);
    }
    
    public PriorityExecutor getExecutor()
    {
        return executor;
    }
}
