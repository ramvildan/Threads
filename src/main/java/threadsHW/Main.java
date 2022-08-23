package threadsHW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        int amountOfNumbers = 1_000_000;
        int numberOfThreads = 4;
        int workOfThreads = amountOfNumbers / numberOfThreads;

        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<ArrayList<Double>> arrays = new ArrayList<>();

        for (int threadNumber = 0; threadNumber < numberOfThreads; threadNumber++) {
            int start = workOfThreads * threadNumber;
            int end = start + workOfThreads;
            ArrayList<Double> numbers = new ArrayList<>(workOfThreads);

            Thread thread = new Thread(() -> {

                for (int i = start; i <= end; i++) {
                    double result = Math.sqrt(i);
                    numbers.add(result);
                }
            });
            threads.add(thread);
            thread.start();
            arrays.add(numbers);
        }
        for (Thread thread : threads) {
            thread.join();
        }

        File file = new File("SQRT.txt");
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        for (ArrayList<Double> line : arrays) {
                            writer.write(String.valueOf(line));
                            writer.newLine();
                        }
                    } catch (IOException exc) {}

        long end = System.currentTimeMillis();
        System.out.println("Took: " + (end - startTime) + " ms");
    }
}