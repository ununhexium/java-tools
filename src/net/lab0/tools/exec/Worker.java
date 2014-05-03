package net.lab0.tools.exec;

/**
 * A worker for the priority executor class. Remembers the last execution time.
 * 
 * @author 116
 * 
 */
public class Worker
extends Thread
{
    private PriorityExecutor executorThread;
    private long                   lastExecution;
    
    @SuppressWarnings("javadoc")
    public Worker(PriorityExecutor executorThread, ThreadGroup group, String name)
    {
        super(group, name);
        setPriority(Thread.MIN_PRIORITY);
        this.executorThread = executorThread;
    }
    
    @Override
    public void run()
    {
        lastExecution = System.currentTimeMillis();
        while (true)
        {
            Runnable runnable = executorThread.getJob();
            
            if (runnable == null)
            {
                if (System.currentTimeMillis() < lastExecution + 1000)
                {
                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    break;
                }
            }
            else
            {
                //System.err.println("Run " + runnable);
                runnable.run();
                executorThread.notifyJobFinished();
                lastExecution = System.currentTimeMillis();
            }
        }
        
        executorThread.notifyDeath(this);
    }
}
