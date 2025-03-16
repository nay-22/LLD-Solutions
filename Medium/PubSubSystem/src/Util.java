package Medium.PubSubSystem.src;
public class Util {
    public static void benchmark(String taskName, Runnable task) {
        long s = System.nanoTime();
        task.run();
        long e = System.nanoTime();
        System.out.println("Task: " + taskName + ", Time: " + ((e - s) / 1_000_000) + " ms");
    }
}
