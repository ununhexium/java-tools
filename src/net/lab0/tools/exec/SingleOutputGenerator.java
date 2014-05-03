package net.lab0.tools.exec;

/**
 * A generator that output the same data as the one that was given as a source.
 * 
 * @author 116
 * 
 * @param <Out>
 */
public class SingleOutputGenerator<Out>
extends Generator<Out, Out>
{
    private boolean once;
    
    public SingleOutputGenerator(PriorityExecutor executor, JobBuilder<Out> jobBuilder, Out source)
    {
        super(executor, jobBuilder, source);
    }
    
    @Override
    public Out generate(Out source)
    {
        if (once)
        {
            return null;
        }
        else
        {
            once = true;
            return source;
        }
    }
}
