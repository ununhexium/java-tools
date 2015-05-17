package net.lab0.tools.math.prime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import net.lab0.tools.HumanReadable;

public class Block
{
    private long   start;
    private int    size;
    private Path   storeLocation;
    private byte[] array;
    
    /**
     * @param start
     *            The start value of this block
     * @param size
     *            The number of elements in this block
     * @param storeLocation
     *            The location where this block will be stored when the data is not used.
     */
    public Block(long start, int size, Path storeLocation)
    {
        super();
        this.start = start;
        this.size = size;
        this.storeLocation = storeLocation;
    }
    
    /**
     * Creates the data on the disk
     * 
     * @throws RuntimeException
     */
    public void create()
    {
        if (storeLocation.toFile().exists())
        {
            throw new IllegalArgumentException("The file already exists " + storeLocation.toAbsolutePath());
        }
        array = new byte[size];
        for (int i = 0; i < size; ++i)
        {
            array[i] = 1;
        }
        save();
    }
    
    /**
     * Deletes the data on the disk
     */
    public void delete()
    {
        if (storeLocation.toFile().exists())
        {
            boolean result = storeLocation.toFile().delete();
            if (!result)
            {
                throw new RuntimeException("Was not able to delete " + storeLocation.toAbsolutePath());
            }
        }
    }
    
    /**
     * Filter out numbers that are multiples of the given primes. Note: The primes themselves are also swept away.
     * 
     * @param primes
     *            The list of primes to use for the Sieves.
     */
    public void filter(int[] primes)
    {
        load();
        for (int p : primes)
        {
            // System.out.println("prime " + p);
            int offset = p - (int) (start % p);
            // offset only if it is required
            offset = offset == p ? 0 : offset;
            for (int i = offset; i < size; i += p)
            {
                // System.out.println("discard " + (start + i));
                array[i] = 0;
            }
        }
        save();
    }
    
    private void load()
    {
        if (!storeLocation.toFile().exists())
        {
            throw new IllegalArgumentException("The file doesn't exists " + storeLocation.toAbsolutePath());
        }
        if (array != null)
        {
            /*
             * already loaded
             */
            return;
        }
        try (
            FileInputStream fos = new FileInputStream(storeLocation.toFile());
            BufferedInputStream bos = new BufferedInputStream(fos))
        {
            array = new byte[size];
            int ret = bos.read(array);
            if (ret != size)
            {
                throw new RuntimeException("Was not able to read the whole array: " + ret + " instead of " + size);
            }
            ret = bos.read();
            if (ret != -1)
            {
                throw new RuntimeException("The file was not read until the end but the whole array was loaded");
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private void save()
    {
        if (array == null)
        {
            throw new IllegalStateException("Tried to save an empty array");
        }
        try (
            FileOutputStream fos = new FileOutputStream(storeLocation.toFile());
            BufferedOutputStream bos = new BufferedOutputStream(fos))
        {
            bos.write(array);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Sets the data array to null so it can be garbage collected
     */
    public void free()
    {
        array = null;
    }
    
    @Override
    public String toString()
    {
        return "Block [start=" + HumanReadable.humanReadableNumber(start) + ", size="
        + HumanReadable.humanReadableNumber(size) + ", storeLocation=" + storeLocation + "]";
    }
    
    public void gather(List<List<Long>> foundPrimes)
    {
        load();
        List<Long> found = new ArrayList<>();
        for (int i = 0; i < size; ++i)
        {
            if (array[i] == 1)
            {
                found.add(start + (long) i);
            }
        }
        foundPrimes.add(found);
    }
    
    public void write(Path path)
    {
        load();
        try (
            FileOutputStream fos = new FileOutputStream(path.toFile(), true);
            PrintWriter pw = new PrintWriter(fos))
        {
            for (int i = 0; i < size; ++i)
            {
                if (array[i] == 1)
                {
                    pw.println(start + (long) i);
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
