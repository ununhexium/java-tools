package net.lab0.tools.exec;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


public class StaticTimer
{
    public static long timeNano(Runnable r){
        long start = System.nanoTime();
        r.run();
        return System.currentTimeMillis() - start;
    }
    
    public static void printNimeNano(Runnable r){
        long start = System.nanoTime();
        r.run();
        long res = System.nanoTime() - start;
        Duration d = Duration.of(res, ChronoUnit.NANOS);
        System.out.println(d);
    }
}
