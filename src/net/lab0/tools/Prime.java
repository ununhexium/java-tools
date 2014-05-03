
package net.lab0.tools;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Prime
{
    public static void main(String[] args)
    throws IOException
    {
        int size = Integer.MAX_VALUE;
        int slices = 5;
        System.out.println("alloc");
        boolean[][] b = new boolean[slices][size];
        System.out.println("init");
        for (int j = 0; j < slices; ++j)
        {
            for (int i = 0; i < size; ++i)
            {
                b[j][i] = true;
            }
        }
        
        System.out.println("sort");
        long cur = 2;
        long index = 0;
        while (cur < (int) (Math.sqrt(slices * size)) + 1)
        {
            index = cur * 2;
            while (index < size && index > 0)
            {
                b[(int) (index / size)][(int) (index % size)] = false;
                index += cur;
            }
            
            cur++;
            while (b[(int) (index / size)][(int) (index % size)] == false)
            {
                cur++;
            }
        }
        
        System.out.println("write");
        try
        {
            PrintWriter pw;
            int fileSerial = 0;
            int counter = 0;
            pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("R:\\dev\\primes\\primes-" + fileSerial + ".txt")), 1 << 20), true);
            for (int i = 0; i < size; ++i)
            {
                if (counter < 1000000)
                {
                    if (b[(int) (i / size)][(int) (i % size)] == true)
                    {
                        pw.println(i);
                        counter++;
                    }
                }
                else
                {
                    System.out.println("File " + fileSerial + " / i=" + i);
                    pw.close();
                    fileSerial++;
                    pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("R:\\dev\\primes\\primes-" + fileSerial + ".txt")), 1 << 20), true);
                    counter = 0;
                }
            }
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
