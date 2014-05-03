package net.lab0.tools.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Some math functions that are not provided by java.
 * 
 * @author 116@lab0.net
 * 
 */
public class MyMath
{
    /**
     * Computes the Fibonacci series using a recursive function.
     * 
     * @param n
     *            The index of the Fibonacci number you want.
     * @return the value of the Nth number in the Fibonacci deries.
     */
    public static long resursiveFibo(int n)
    {
        if (n < 2)
        {
            return 1;
        }
        
        return resursiveFibo(n - 1) + resursiveFibo(n - 2);
    }
    
    /**
     * Does a decomposition of the number in prime factors.
     * 
     * @param n
     *            The number to decompose.
     * @return A list containing the prime factors. If this list is empty, then n is a prime number.
     */
    public static List<Long> primeDecomposition(long n)
    {
        List<Long> factors = new ArrayList<>();
        
        // special case for the pair numbers -> allows a +=2 in the next loop
        long i = 2;
        while (n % i == 0)
        {
            n /= i;
            factors.add(i);
        }
        i++;
        
        while (i <= Math.sqrt(n))
        {
            while (n % i == 0)
            {
                n /= i;
                factors.add(i);
            }
            i += 2;
        }
        factors.add(n);
        
        return factors;
    }
    
    /**
     * Test if the number is a palindrome.
     * 
     * @param l
     *            The number to test.
     * @return <code>true</code> if the number is a palindrome. <code>false</code> otherwise.
     */
    public static boolean isPalindrome(long l)
    {
        String s = Long.toString(l);
        for (int i = 0; i < s.length(); ++i)
        {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i))
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * @see MyMath#isPalindrome(long)
     */
    public static boolean isPalindrome(BigInteger bi)
    {
        String s = bi.toString();
        for (int i = 0; i < s.length(); ++i)
        {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i))
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Computes a list of all the primes number between 2 and <code>length</code> included using the Sieve of
     * Eratosthenes.
     * 
     * @param length
     *            search primes numbers up to this value.
     * @return A list of primes numbers.
     */
    public static List<Integer> getPrimes(int length)
    {
        boolean[] array = new boolean[length];
        // these are nor primes
        array[0] = true;
        array[1] = true;
        int step = 2;
        while (step * step < length && step <= Math.sqrt(Integer.MAX_VALUE) + 1) // this second condition is for
                                                                                 // overflow checking when length =
                                                                                 // Integer.MAX_VALUE
        {
            // System.out.println("step=" + step);
            for (int i = 2 * step; i < array.length && i >= 0; i += step)
            {
                array[i] = true;
            }
            
            step++;
            while (array[step])
            {
                step++;
            }
        }
        
        List<Integer> primes = new ArrayList<>();
        for (int i = 0; i < length; i++)
        {
            if (!array[i])
            {
                primes.add(i);
            }
        }
        
        return primes;
    }
    
    /**
     * Computes the Nth triangular number.
     * 
     * @param n
     *            The index of the triangular number to compute.
     * @return The triangular number.
     */
    public static long triangular(long n)
    {
        long result = 0;
        for (int i = 0; i <= n; i++)
        {
            result += i;
        }
        
        return result;
    }
    
    /**
     * Finds the divisors for the given number.
     * 
     * @param n
     *            Find the divisors for this number.
     * @return A list of divisors for this number.
     */
    public static List<Long> divisors(long n)
    {
        List<Long> divisors = new ArrayList<>();
        long i = 1;
        for (; i * i < n; i++)
        {
            if (n % i == 0)
            {
                divisors.add(i);
                divisors.add(n / i);
            }
        }
        
        if (i * i == n)
        {
            divisors.add(i);
        }
        
        return divisors;
    }
    
    /**
     * Computes the Collatz sequence for N. WARNING: there is no proof of termination for this algorithm.
     * 
     * @param n
     *            The start of the collatz sequence.
     * @return The list to put the computed values in.
     */
    public static List<Long> collatz(long n)
    {
        List<Long> list = new ArrayList<>();
        while (n >= 2)
        {
            // System.out.println(n);
            list.add(n);
            
            if (n % 2L == 0L)
            {
                n = n / 2L;
            }
            else
            {
                n = 3L * n + 1L;
            }
        }
        
        list.add(1L);
        
        return list;
    }
    
    /**
     * Factorial function. Can be usefull sometimes.
     * 
     * @param n
     * @return n!
     */
    public static BigInteger fact(int n)
    {
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; ++i)
        {
            result = result.multiply(new BigInteger("" + i));
        }
        return result;
    }
    
    /**
     * Like ... seriously ... writing a function to convert a figure to letters ...
     * 
     * @param value
     *            some shit
     * @return A much bigger shit
     */
    public static String toAlphaEnglish(int value)
    {
        StringBuilder sb = new StringBuilder();
        
        // max value, then go fuck yourself
        if (value > 999)
        {
            int thousands = value / 1000;
            sb.append(toAlphaEnglish(thousands) + "thousand ");
            value = value - thousands * 1000;
        }
        
        // ok this was easy
        if (value > 99)
        {
            int hundreds = value / 100;
            sb.append(toAlphaEnglish(hundreds) + "hundred ");
            value = value - 100 * hundreds;
            if (value > 0)
            {
                sb.append("and ");
            }
        }
        
        // I should do a secretary job. I really miss all this text typing -_-
        if (value > 19)
        {
            int tens = value / 10;
            switch (tens)
            {
                case 2:
                    sb.append("twenty");
                    break;
                case 3:
                    sb.append("thirty");
                    break;
                case 4:
                    sb.append("forty");
                    break;
                case 5:
                    sb.append("fifty");
                    break;
                case 6:
                    sb.append("sixty");
                    break;
                case 7:
                    sb.append("seventy");
                    break;
                case 8:
                    sb.append("eighty");
                    break;
                case 9:
                    sb.append("ninety");
                    break;
            }
            value = value - tens * 10;
            
            if (value > 0)
            {
                sb.append("-");
            }
        }
        
        // 1 plus 1 equals ONE.
        // life is so different on this side
        // it's beautiful
        // they should have send a poet.
        if (value > 0)
        {
            switch (value)
            {
                case 1:
                    sb.append("one ");
                    break;
                
                case 2:
                    sb.append("two ");
                    break;
                
                case 3:
                    sb.append("three ");
                    break;
                
                case 4:
                    sb.append("four ");
                    break;
                
                case 5:
                    sb.append("five ");
                    break;
                
                case 6:
                    sb.append("six ");
                    break;
                
                case 7:
                    sb.append("seven ");
                    break;
                
                case 8:
                    sb.append("eight ");
                    break;
                
                case 9:
                    sb.append("nine ");
                    break;
                
                case 10:
                    sb.append("ten ");
                    break;
                
                case 11:
                    sb.append("eleven ");
                    break;
                
                case 12:
                    sb.append("twelve ");
                    break;
                
                case 13:
                    sb.append("thirteen ");
                    break;
                
                case 14:
                    sb.append("fourteen ");
                    break;
                
                case 15:
                    sb.append("fifteen ");
                    break;
                
                case 16:
                    sb.append("sixteen ");
                    break;
                
                case 17:
                    sb.append("seventeen ");
                    break;
                
                case 18:
                    sb.append("eighteen ");
                    break;
                
                case 19:
                    sb.append("nineteen ");
                    break;
            }
        }
        // DESTROY
        // ANIHALATE
        // OBLITERATE
        
        return sb.toString();
    }
    
    /**
     * 
     * @param n
     *            the number
     * @return The list of the proper divisors of <code>n</code>.
     */
    public static List<Long> properDivisors(long n)
    {
        List<Long> divisors = new ArrayList<>();
        for (long i = 1L; i <= n / 2L; ++i)
        {
            if (n % i == 0)
            {
                divisors.add(i);
            }
        }
        
        return divisors;
    }
    
    /**
     * Basic implementation to test whether a number is prime. Uses a simple method: tests divisibility by every odd
     * number and 2.
     * 
     * @return <code>true</code> if the number is prime, <code>false</code> otherwise.
     */
    public static boolean isPrime(long value)
    {
        if (value < 2L)
        {
            return false;
        }
        
        if (value == 2)
        {
            return true;
        }
        
        if (value % 2 == 0)
        {
            return false;
        }
        
        long i = 3;
        while (i * i <= value)
        {
            if (value % i == 0)
            {
                return false;
            }
            i += 2;
        }
        
        return true;
    }
    
    /**
     * Sums the numbers in the given collection as longs. Doesn't handle overflows.
     * 
     * @param collection
     *            The collection containing the numbers to sum
     */
    public static long longSum(Collection<? extends Number> collection)
    {
        long total = 0;
        for (Number n : collection)
        {
            total += n.longValue();
        }
        return total;
    }
    
    public static long power(long number, int exponent)
    {
        if (exponent < 0)
        {
            throw new IllegalArgumentException("The exponent (" + exponent + ") can't be negative");
        }
        if (exponent == 0)
        {
            return 1;
        }
        
        if (exponent % 2 == 0)
        {
            return power(number, exponent / 2) * power(number, exponent / 2);
        }
        else
        {
            return power(number, exponent - 1) * number;
        }
    }
}
