package net.lab0.tools.math.prime;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import net.lab0.tools.FileUtils;
import net.lab0.tools.HumanReadable;
import net.lab0.tools.math.MyMath;
import net.lab0.tools.stream.For;

/**
 * Applies a sieve of Eratosthenes on a large scale.
 * 
 * @author 116@lab0.net
 *
 */
public class BigPrimesFinder
{
    public static enum Verbose
    {
        /**
         * Activate System.out
         */
        ON,
        /**
         * Disable System.out
         */
        OFF;
    }
    
    private Verbose verbose;
    
    /**
     * 
     * @param M
     *            Find prime numbers up to at least M-1 value.
     * @param tmpPath
     * @param outputPath
     *            The path where the results will be stored
     * @param verbose
     *            Display progress messages ?
     * @throws IOException
     */
    public void findPrimes(long M, Path tmpPath, Path outputPath, Verbose verbose) throws IOException
    {
        if (M > Long.MAX_VALUE / 2)
        {
            throw new IllegalArgumentException("The value of max can't be over Long.MAX_VALUE / 2");
        }
        this.verbose = verbose;
        
        int root = (int) Math.sqrt(M) + 1;
        message("Computing primes up to " + root + " with the classic algorithm");
        List<Integer> primesList = MyMath.getPrimes(root);
        int[] primes = primesList.stream().mapToInt((l) -> l.intValue()).toArray();
        message("Computed the primes from " + primes[0] + " to " + primes[primes.length - 1]);
        
        int blocksCount = Runtime.getRuntime().availableProcessors() * 10;
        /*
         * Set max block size
         */
        long _128MB = 128L * 1024L * 1024L;
        while (M / blocksCount > _128MB)
        {
            blocksCount++;
        }
        /*
         * Set min block size to 1M
         */
        if (M > (2 << 20))
        {
            while ((M / blocksCount) < 2 << 20)
            {
                blocksCount--;
            }
        }
        
        int blockSize = (int) ((M / blocksCount) + 2);
        message("Blocks count " + blocksCount);
        message("Blocks size " + HumanReadable.humanReadableNumber(blockSize));
        
        tmpPath.toFile().mkdirs();
        String filename = "serializedBlock";
        int next = FileUtils.getNextAvailablePath(tmpPath, filename);
        List<Block> blocks = new ArrayList<Block>(blocksCount);
        For.n((long) blocksCount).forEach((n) -> blocks.add(new Block(n * (long) blockSize,
                                                                      blockSize,
                                                                      tmpPath.resolve(filename + (next + n)))));
        /*
         * Actual computation
         */
        List<List<Long>> foundPrimes = new ArrayList<>();
        foundPrimes.add(primesList.stream().mapToLong((i) -> i.longValue()).boxed().collect(Collectors.toList()));
        AtomicInteger counter = new AtomicInteger(0);
        blocks.parallelStream().forEach((b) -> {
            counter.incrementAndGet();
            message("Processing " + counter.get() + "/" + blocks.size() + " " + b);
            b.delete();
            b.create();
            b.filter(primes);
            b.free();
        });
        
        message("Writing " + outputPath.toAbsolutePath());
        /*
         * Cleanup any existing file because we are going to append all the results and don't want any duplicate.
         */
        outputPath.toFile().delete();
        /*
         * Copy the primes used because: they've been deleted during the previous operations.
         */
        try (
            FileOutputStream fos = new FileOutputStream(outputPath.toFile(), true);
            PrintWriter pw = new PrintWriter(fos))
        {
            for (long p : primesList)
            {
                pw.println(p);
            }
        }
        /*
         * Append all the primes numbers we found in the existing list in ascending order.
         */
        blocks.stream().forEach((b) -> {
            b.write(outputPath);
            b.free();
            b.delete();
        });
        
        /*
         * problem due to the arrays starting at 0: quickfix 0 and 1 to be non prime numbers.
         */
        foundPrimes.remove(0L);
        foundPrimes.remove(1L);
        /*
         * Cleanup temporary folders
         */
        FileUtils.removeRecursive(tmpPath);
    }
    
    private void message(String message)
    {
        if (verbose == Verbose.ON)
        {
            System.out.println(message);
        }
    }
    
    /**
     * Usage example of {@link BigPrimesFinder}
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        long start = System.nanoTime();
        Path out = FileSystems.getDefault().getPath("R:", "dev", "primes.txt");
        Path basePath = FileSystems.getDefault().getPath("R:", "tmp", "primes");
        long max = 1_000_000_000_000L;
        System.out.println("Searching primes up to " + HumanReadable.humanReadableNumber(max));
        new BigPrimesFinder().findPrimes(max, basePath, out, Verbose.ON);
        
        long count = 0;
        String maxPrime = null;
        try (
            FileReader fr = new FileReader(out.toFile());
            BufferedReader br = new BufferedReader(fr))
        {
            while (true)
            {
                String tmp = br.readLine();
                if (tmp != null)
                {
                    maxPrime = tmp;
                    count++;
                }
                else
                {
                    break;
                }
            }
        }
        System.out.println("Found " + count + " primes. Max prime=" + maxPrime);
        System.out.println("Duration "
        + HumanReadable.humanReadableNumber((System.nanoTime() - start) / 1_000_000_000L, true, "s"));
    }
}
