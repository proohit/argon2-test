/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package argon2.tests;

import java.util.ArrayList;
import java.util.List;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class App {
    /*
     * Parameters:
     * [0] = memory in KB
     * [1] = iterations
     * [2] = parallelism
     */
    private static final int[][] parameterTable = {
            { 1024, 1, 1 },
            { 1024, 2, 1 },
            { 1024, 1, 2 },
            { 1024, 2, 2 },
            { 2048, 1, 1 },
            { 2048, 2, 1 },
            { 2048, 1, 2 },
            { 2048, 2, 2 },
            // 500 MB 16 Iterations, 32 Threads
            { 1024 * 500, 8, 16 },
            // 2 GB 16 Iterations, 32 Threads
            { 1024 * 1024 * 2, 8, 16 },
    };

    public static void main(String[] args) {
        Argon2 argon2 = Argon2Factory.create();
        argon2.hash(1, 1024, 1, "password".toCharArray());
        List<Result> results = new ArrayList<>();

        for (int[] parameter : parameterTable) {
            int memory = parameter[0];
            int iterations = parameter[1];
            int parallelism = parameter[2];
            long start = System.nanoTime();
            argon2.hash(iterations, memory, parallelism, "password".toCharArray());
            long end = System.nanoTime();
            results.add(
                    new Result(memory, iterations, parallelism, memory * iterations, (float) (end - start) / 1000000));
        }

        System.out.format("%8s%16s%16s%32s%16s%n", "memory", "iterations", "parallelism", "total memory usage",
                "time (ms)");
        for (Result result : results) {
            System.out.format("%8d%16d%16d%32d%16f%n", result.memory, result.iterations, result.parallelism,
                    result.totalMemoryUsage, result.time);
        }
    }

    static class Result {
        public final int memory;
        public final int iterations;
        public final int parallelism;
        public final int totalMemoryUsage;
        public final float time;

        public Result(int memory, int iterations, int parallelism, int totalMemoryUsage, float time) {
            this.memory = memory;
            this.iterations = iterations;
            this.parallelism = parallelism;
            this.totalMemoryUsage = totalMemoryUsage;
            this.time = time;
        }
    }

}
