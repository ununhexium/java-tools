package net.lab0.tools;

import java.util.Random;

public class StaticRandom
{
    public static final Random random = new Random(System.currentTimeMillis());
    
    /**
     * Builds an array of random integers
     * 
     * @param size
     *            The size of the returned array.
     * @param min
     *            The minimum value an element of the array can have (included).
     * @param max
     *            The maximum value an element of the array can have (excluded).
     * @return An array of integers with the given parameters.
     */
    public static int[] randomArray(int size, int min, int max)
    {
        int[] ret = new int[size];
        int diff = max - min;
        for (int i = 0; i < size; ++i)
        {
            ret[i] = random.nextInt(diff) + min;
        }
        return ret;
    }
    
    public static void main(String[] args)
    {
        for (int i = 0; i < 100; ++i)
        {
            for (int r : randomArray(10, 116, 125))
            {
                System.out.print(r+" ");
            }
            System.out.println();
        }
    }
}
