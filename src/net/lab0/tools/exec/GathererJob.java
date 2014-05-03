package net.lab0.tools.exec;

/**
 * A job that receives several input for a single output.
 * 
 * @author 116
 * 
 */
public abstract class GathererJob<In, Out>
extends CascadingJob<In, Out>
{
    protected Out aggregate;
    private In    input;
    
    public GathererJob(CascadingJob<?, In> parent, In input, Out aggregate)
    {
        super(parent, null);
        this.aggregate = aggregate;
        this.input = input;
    }
    
    public Out getAggregate()
    {
        return aggregate;
    }
    
    @Override
    public final void executeTask()
    throws Exception
    {
        aggregate(input);
    }
    
    public abstract void aggregate(In input);
}
