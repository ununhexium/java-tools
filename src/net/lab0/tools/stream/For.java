package net.lab0.tools.stream;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * <code>for</code> loop as a stream.
 * 
 * @author 116@lab0.net
 *
 */
public class For
{
    /**
     * 
     * @param n
     * @return A range [0;n[
     */
    public static LongStream n(long n)
    {
        return LongStream.range(0, n);
    }
    
    /**
     * 
     * @param n
     * @return A range [0;n[
     */
    public static IntStream n(int n)
    {
        return IntStream.range(0, n);
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return A range [a;b[
     */
    public static LongStream ab(long a, long b)
    {
        return LongStream.range(a, b);
    }
}
