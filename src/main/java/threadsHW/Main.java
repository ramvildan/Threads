package threadsHW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static int amountOfNumbers = 1_000_000;
    private static int numberOfThreads = 4;
    private static int workOfThreads = amountOfNumbers / numberOfThreads;
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static double[] arrayResults = new double[amountOfNumbers];
    private static ArrayList<Double> results = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            startThread(i);
        }
        joinAll();

        for (int i = 0; i < amountOfNumbers; i++) {
            double result = arrayResults[i];
            results.add(result);
        }

        fileWriter("SQRT.txt");

        long end = System.currentTimeMillis();
        System.out.println("Took: " + (end - startTime) + " ms");
    }

    public static void startThread(int threadNumber) {
        Thread thread = new Thread(() -> {
                for (int i = 1; i < workOfThreads; i++) {
                    double SQRTResult = Math.sqrt(i);
                    arrayResults[threadNumber * workOfThreads + i] = SQRTResult;
                }
        });
        thread.start();
        threads.add(thread);
    }

    private static void joinAll () throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void fileWriter(String filepath) {
        File file = new File(filepath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Double line : results) {
                writer.write(String.valueOf(line));
                writer.newLine();
            }
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}