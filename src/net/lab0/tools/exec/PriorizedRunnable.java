package net.lab0.tools.exec;

/**
 * To use in conjunction with {@link PriorityExecutor} to choose the priority of execution of the processes. The default
 * priority is 0. The runnables are executed; higher priority first.
 * 
 * @author 116@lab0.net
 * 
 */
public interface PriorizedRunnable
extends Runnable, Comparable<PriorizedRunnable>
{
    /**
     * @return The priority associated to this {@link PriorizedRunnable}
     */
    public int getPriority();
}
