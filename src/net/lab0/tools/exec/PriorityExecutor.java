package net.lab0.tools.exec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Executes the {@link PriorizedRunnable}s
 * 
 * @author 116
 * 
 */
public class PriorityExecutor
{
    private int                                      maxThreadCount = 1;
    private PriorityBlockingQueue<PriorizedRunnable> queue          = new PriorityBlockingQueue<>();
    private HashSet<Worker>                          workers        = new HashSet<>();
    private List<Runnable>                           shutdownHooks  = Collections
                                                                    .synchronizedList(new ArrayList<Runnable>());
    private ThreadGroup                              threadGroup    = new ThreadGroup(
                                                                    PriorityExecutor.class.getSimpleName()
                                                                    + " group");
    private long                                     workerId       = 0;
    
    private Thread                                   checkerThread;
    private boolean                                  finished;
    private int                                      running;
    private int                                      modCount;
    
    /**
     * Creates a {@link PriorityExecutor} with the given number of threads and timeout.
     * 
     * @param maxThreadCount
     *            The maximum number of threads to use to execute the jobs. Minimum value: 1.
     * @param timeout
     *            The timeout before it can be considered that no new job will be added.
     * @param unit
     *            The unit of the timeout
     */
    public PriorityExecutor(int maxThreadCount, long timeout, TimeUnit unit)
    {
        if (maxThreadCount <= 0)
        {
            throw new IllegalArgumentException(
            "The minimum acceptable number of threads is 1. maxThreadCount must be >= 1");
        }
        this.maxThreadCount = maxThreadCount;
        
        // check for at least 2 seconds if no new job is retrieved
        checkerThread = new CheckerThread(threadGroup, this, modCount, timeout, unit, unit.toMillis(timeout) / 4 + 1,
        TimeUnit.MILLISECONDS);
        checkerThread.start();
    }
    
    /**
     * Creates a {@link PriorityExecutor} with the given number of threads.
     * 
     * @param maxThreadCount
     *            The maximum number of threads to use to execute the jobs. Minimum value: 1.
     */
    public PriorityExecutor(int maxThreadCount)
    {
        this(maxThreadCount, 2, TimeUnit.SECONDS);
    }
    
    /**
     * Creates a {@link PriorityExecutor} with 1 thread.
     */
    public PriorityExecutor()
    {
        this(1, 2, TimeUnit.SECONDS);
    }
    
    /**
     * Adds an element to execute. Returns immediately.
     * 
     * @param runnable
     *            The runnable to be executed. <code>null</code> not accepted.
     * @throws NullPointerException
     */
    public synchronized void execute(PriorizedRunnable runnable)
    throws NullPointerException
    {
        // System.err.println("exec " + runnable);
        queue.add(runnable);
        
        if (workers.size() < maxThreadCount && queue.peek() != null)
        {
            // System.err.println("new worker");
            Worker worker = new Worker(this, threadGroup, "Worker-" + workerId++);
            workers.add(worker);
            worker.start();
            modCount++;
            
            Set<Worker> inactive = new HashSet<>();
            for (Worker w : workers)
            {
                if (!w.isAlive())
                {
                    try
                    {
                        w.join();
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    inactive.add(w);
                }
            }
            workers.removeAll(inactive);
        }
    }
    
    /**
     * @return The next job to execute or <code>null</code> if nothing to execute.
     */
    public synchronized Runnable getJob()
    {
        PriorizedRunnable runnable = queue.poll();
        if (runnable != null)
        {
            running++;
            // System.err.println("Running " + running);
        }
        // System.err.println("get " + runnable);
        return runnable;
    }
    
    /**
     * Register a runnable that will be executed after the completion of the execution chain. If several hooks are
     * registered, they can be executed in an arbitrary order.
     * 
     * @param hook
     *            The runnable to register.
     */
    public void registerShutdownHook(Runnable hook)
    {
        this.shutdownHooks.add(hook);
    }
    
    /**
     * Notify the end of a worker's job.
     */
    synchronized void notifyJobFinished()
    {
        running--;
        // System.err.println("Running " + running);
    }
    
    /**
     * Starts the termination of this executor. All the jobs and they dependencies are run. Once no more job is
     * submitted, executes the shutdown hooks and terminates.
     * 
     * @throws InterruptedException
     */
    public synchronized void waitForFinish()
    throws InterruptedException
    {
        while (!finished)
        {
            // System.err.println("wait");
            wait();
        }
        
        checkerThread.join();
        // System.err.println("out");
        // System.err.println("Running " + running);
        // System.err.println("Finished " + finished);
        // System.err.println("MOD " + modCount);
    }
    
    /**
     * @param worker
     *            The worker that is dying
     */
    public synchronized void notifyDeath(Worker worker)
    {
        // System.err.println("death");
        workers.remove(worker);
        notifyAll();
    }
    
    public int getRunning()
    {
        return running;
    }
    
    public int getModCount()
    {
        return modCount;
    }
    
    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }
    
}
