package net.lab0.tools.exec;

/**
 * 
 * @author 116
 * 
 * @param <D>
 *            The output of the process using this JobBuilder and the input of the process that will be created.
 */
public interface JobBuilder<D>
{
    /**
     * Creates a cascaded job with the output of this job as its input.
     * 
     * @param output
     *            The output of the job owning the JobBuilder.
     * @return The next {@link CascadingJob} that will be added to the executor.
     */
    public CascadingJob<D, ?> buildJob(CascadingJob<?, D> parent, D output);
}
