package net.lab0.tools.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MyMathTest
{
    public static List<Integer> primes = Arrays.asList(2,
                                                       3,
                                                       5,
                                                       7,
                                                       11,
                                                       13,
                                                       17,
                                                       19,
                                                       23,
                                                       29,
                                                       31,
                                                       37,
                                                       41,
                                                       43,
                                                       47,
                                                       53,
                                                       59,
                                                       61,
                                                       67,
                                                       71,
                                                       73,
                                                       79,
                                                       83,
                                                       89,
                                                       97,
                                                       101,
                                                       103,
                                                       107,
                                                       109,
                                                       113,
                                                       127,
                                                       131,
                                                       137,
                                                       139,
                                                       149,
                                                       151,
                                                       157,
                                                       163,
                                                       167,
                                                       173,
                                                       179,
                                                       181,
                                                       191,
                                                       193,
                                                       197,
                                                       199,
                                                       211,
                                                       223,
                                                       227,
                                                       229,
                                                       233,
                                                       239,
                                                       241,
                                                       251,
                                                       257,
                                                       263,
                                                       269,
                                                       271,
                                                       277,
                                                       281,
                                                       283,
                                                       293,
                                                       307,
                                                       311,
                                                       313,
                                                       317,
                                                       331,
                                                       337,
                                                       347,
                                                       349,
                                                       353,
                                                       359,
                                                       367,
                                                       373,
                                                       379,
                                                       383,
                                                       389,
                                                       397,
                                                       401,
                                                       409,
                                                       419,
                                                       421,
                                                       431,
                                                       433,
                                                       439,
                                                       443,
                                                       449,
                                                       457,
                                                       461,
                                                       463,
                                                       467,
                                                       479,
                                                       487,
                                                       491,
                                                       499,
                                                       503,
                                                       509,
                                                       521,
                                                       523,
                                                       541,
                                                       547,
                                                       557,
                                                       563,
                                                       569,
                                                       571,
                                                       577,
                                                       587,
                                                       593,
                                                       599,
                                                       601,
                                                       607,
                                                       613,
                                                       617,
                                                       619,
                                                       631,
                                                       641,
                                                       643,
                                                       647,
                                                       653,
                                                       659,
                                                       661,
                                                       673,
                                                       677,
                                                       683,
                                                       691,
                                                       701,
                                                       709,
                                                       719,
                                                       727,
                                                       733,
                                                       739,
                                                       743,
                                                       751,
                                                       757,
                                                       761,
                                                       769,
                                                       773,
                                                       787,
                                                       797,
                                                       809,
                                                       811,
                                                       821,
                                                       823,
                                                       827,
                                                       829,
                                                       839,
                                                       853,
                                                       857,
                                                       859,
                                                       863,
                                                       877,
                                                       881,
                                                       883,
                                                       887,
                                                       907,
                                                       911,
                                                       919,
                                                       929,
                                                       937,
                                                       941,
                                                       947,
                                                       953,
                                                       967,
                                                       971,
                                                       977,
                                                       983,
                                                       991,
                                                       997,
                                                       1009,
                                                       1013,
                                                       1019,
                                                       1021);
    
    @Test
    public void testIsPrime()
    {
        for (int i = 0; i < 1022; ++i)
        {
            boolean isPrime = MyMath.isPrime(i);
            Assert.assertEquals("" + i, primes.contains(i), isPrime);
        }
    }
    
    @Test
    public void testPrimeDecomposition()
    {
        Assert.assertTrue(MyMath.primeDecomposition(0).size() == 0);
        Assert.assertTrue(MyMath.primeDecomposition(1).size() == 0);
        Assert.assertTrue(MyMath.primeDecomposition(-1).size() == 0);
        
        List<Long> p128 = Arrays.asList(2L, 2L, 2L, 2L, 2L, 2L, 2L);
        Assert.assertArrayEquals(p128.toArray(), MyMath.primeDecomposition(128).toArray());
        
        List<Long> p238 = Arrays.asList(2L, 7L, 17L);
        Assert.assertArrayEquals(p238.toArray(), MyMath.primeDecomposition(238).toArray());
        
        List<Long> p375 = Arrays.asList(3L, 5L, 5L, 5L);
        Assert.assertArrayEquals(p375.toArray(), MyMath.primeDecomposition(375).toArray());
        
    }
    
    @Test
    public void testGetPrimes()
    {
        int count = 0;
        for (int p : primes)
        {
            count++;
            Assert.assertEquals(count, MyMath.getPrimes(p).size());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetPrimes0()
    {
        MyMath.getPrimes(0);
        Assert.fail("Exception expected");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetPrimes1()
    {
        MyMath.getPrimes(1);
        Assert.fail("Exception expected");
    }
}
