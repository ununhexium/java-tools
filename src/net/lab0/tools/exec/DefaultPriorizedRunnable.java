package net.lab0.tools.exec;

/**
 * Dummy implementation of the {@link PriorizedRunnable} interface. The run methods waits for 1000ms before returning.
 * 
 * @author 116@lab0.net
 * 
 */
public class DefaultPriorizedRunnable
implements PriorizedRunnable
{
    private int priority;
    
    /**
     * Creates a runnable with the given priority. The highest priority runnables will be executed before the lower
     * priority runnables.
     * 
     * @param priority
     */
    public DefaultPriorizedRunnable(int priority)
    {
        super();
        this.priority = priority;
    }
    
    @Override
    public int getPriority()
    {
        return priority;
    }
    
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public int compareTo(PriorizedRunnable o)
    {
        return o.getPriority() - this.getPriority();
    }
}
