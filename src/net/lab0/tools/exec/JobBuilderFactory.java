package net.lab0.tools.exec;

import java.util.List;

public class JobBuilderFactory
{
    public static class DevNull<T>
    extends CascadingJob<T, Void>
    {
        DevNull(CascadingJob<?, T> parent)
        {
            super(parent.getExecutor(), Integer.MAX_VALUE - 1, null);
        };
        
        @Override
        public void executeTask()
        throws Exception
        {
            return;
        }
    }
    
    /**
     * Sends the data to a sumper that will put all the data in the given <code>dumpingList</code>.
     * 
     * @param dumpingList
     * @return
     */
    public static synchronized <T> JobBuilder<T> toDumper(final List<T> dumpingList)
    {
        return new JobBuilder<T>()
        {
            @Override
            public CascadingJob<T, ?> buildJob(CascadingJob<?, T> parent, T output)
            {
                return new Dump<T>(parent.getExecutor(), output, dumpingList);
            }
        };
    }
    
    /**
     * To discard the output data.
     * 
     * @return A builder that will send the data to an equivalent of /dev/null
     */
    public static synchronized <T> JobBuilder<T> toDevNull()
    {
        return new JobBuilder<T>()
        {
            @Override
            public CascadingJob<T, ?> buildJob(CascadingJob<?, T> parent, final T output)
            {
                return new DevNull<T>(parent);
            }
        };
    }
}
