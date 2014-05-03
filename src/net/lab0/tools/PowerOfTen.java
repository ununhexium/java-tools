package net.lab0.tools;



public enum PowerOfTen
{
    _1(1L),
    _10(10L),
    _100(100L),
    _1k(1_000L),
    _10k(10_000L),
    _100k(100_000L),
    _1M(1_000_000L),
    _10M(10_000_000L),
    _100M(100_000_000L),
    _1G(1_000_000_000L),
    _10G(10_000_000_000L),
    _100G(100_000_000_000L),
    _1T(1_000_000_000_000L),
    _10T(10_000_000_000_000L),
    _100T(100_000_000_000_000L),
    _1P(1_000_000_000_000_000L),
    _10P(10_000_000_000_000_000L),
    _100P(100_000_000_000_000_000L),
    _1E(1_000_000_000_000_000_000L),
    
    ;
    
    public final long           value;
    
    private PowerOfTen(long quantity)
    {
        this.value = quantity;
    }
    
    @Override
    public String toString()
    {
        return "" + HumanReadable.humanReadableNumber(value);
    }
    
}
