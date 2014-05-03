package net.lab0.tools.exec;

import java.util.concurrent.TimeUnit;

public class CheckerThread
extends Thread
{
    private PriorityExecutor priorityExecutor;
    private int                    lastModCount;
    private long                   lastCheckTime;
    private long                   timeout;         // ms
    private long                   lapse;           // ms
    private boolean                dying = false;
    
    public CheckerThread(ThreadGroup group, PriorityExecutor priorityExecutor, int lastModCount, long timeout,
    TimeUnit timeoutTimeUnit, long lapse, TimeUnit lapseTimeUnit)
    {
        super(group, "Checker");
        this.priorityExecutor = priorityExecutor;
        this.lastModCount = lastModCount;
        this.lastCheckTime = System.currentTimeMillis();
        this.timeout = timeoutTimeUnit.toMillis(timeout);
        this.lapse = lapseTimeUnit.toMillis(lapse);
        this.setPriority(Thread.NORM_PRIORITY);
    }
    
    @Override
    public void run()
    {
        synchronized (this)
        {
            // the only way to get out of here is to finish the execution of the priority executor
            while (true)
            {
                if (priorityExecutor.getRunning() != 0 || lastModCount != priorityExecutor.getModCount())
                {
                    dying = false;
                    lastModCount = priorityExecutor.getModCount();
                }
                else
                {
                    if (dying)
                    {
//                        System.err.println("Dying " + (lastCheckTime - System.currentTimeMillis() + timeout));
                        if (System.currentTimeMillis() > lastCheckTime + timeout)
                        {
                            priorityExecutor.setFinished(true);
//                            System.err.println("Checker stop signal");
                            break;
                        }
                        else
                        {
                            try
                            {
                                Thread.sleep(lapse);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        lastCheckTime = System.currentTimeMillis();
                        dying = true;
                    }
                }
            }
        }
        synchronized (priorityExecutor)
        {
            priorityExecutor.notifyAll();
        }
//        System.err.println("Checker dead");
    }
    
}
