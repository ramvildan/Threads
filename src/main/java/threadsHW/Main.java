package threadsHW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static  Object lock = new Object();
    private static double number = 0;
    private static double numberSqrt = 0;
    private static double endCounter = 1_000_000;
    private static double numberOfThreads = 10;
    private static List<Double> sqrt = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        ArrayList<Thread> threads = new ArrayList<>();

        for (int threadNumber = 0; threadNumber < numberOfThreads; ++threadNumber) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < (endCounter / numberOfThreads); ++i) {
                synchronized (lock) {
                    number++;
                }
                    numberSqrt = Math.sqrt(number);
                    sqrt.add(numberSqrt);
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        File file = new File("SQRT.txt");
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        for (Double line : sqrt) {
                            writer.write(String.valueOf(line));
                            writer.newLine();
                        }
                    } catch (IOException exc) {}

        long end = System.currentTimeMillis();
        System.out.println("Took: " + (end - startTime) + " ms");
    }
}