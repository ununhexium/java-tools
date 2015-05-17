package net.lab0.tools;

/**
 * 
 * @author 116@lab0.net
 * 
 *         From
 * @see http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
 * 
 */
public class HumanReadable
{
    /**
     * Human readable with SI scale (10^3)
     */
    public static String humanReadableNumber(long quantity)
    {
        return humanReadableNumber(quantity, true, "");
    }
    
    /**
     * Human readable with information science scale (2^10)
     */
    public static String humanReadableSizeInBytes(long quantity)
    {
        return humanReadableNumber(quantity, false, "B");
    }
    
    /**
     * Converts a quantity to a human readable string with standard units (kilo, mega, ...)
     * 
     * @param quantity
     *            the quantity to convert
     * @param si
     *            if <code>true</code>: uses the SI system. Otherwise uses the 1024 units scale.
     * @return a <code>String</code> representing a amount <code>quantity</code>.
     */
    public static String humanReadableNumber(long quantity, boolean si, String unitName)
    {
        long unit = si ? 1000L : 1024L;
        if (quantity < unit)
            return Long.toString(quantity) + unitName;
        int exp = (int) (Math.log(quantity) / Math.log(unit));
        String pre = "kMGTPE".charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %s", quantity / Math.pow(unit, exp), pre) + unitName;
    }
    
    public static void main(String[] args)
    {
        System.out.println(humanReadableSizeInBytes(1024L * 1024L * 1024L * 1024L));// 1 TiB
        System.out.println(humanReadableNumber(1000_000_000L));// 1G
    }
}
