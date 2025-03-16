package Easy.LoggingFramework;

import Easy.LoggingFramework.thirdPartyApp.UserManager;

public class Main {
    public static void main(String[] args) {

        benchmark("APP BENCHMARK", () -> {
            System.out.println("*".repeat(40) + " START APP " + "*".repeat(40));

            UserManager userManager = new UserManager();

            benchmark("User registration", () -> {
                userManager.registerUser("zoro@onepiece.com", "Roronoa Zoro");
            });

            benchmark("User registration", () -> {
                userManager.registerUser("luffy@onepiece.com", "Monkey D. Luffy");
            });

            benchmark("Get user by email", () -> {

                userManager.getUserByEmail("zoro@onepiece.com").ifPresent(System.out::println);
            });

            benchmark("Invalid user de-registration", () -> {
                userManager.deregisterUser("nami@onepiece.com");
            });

            benchmark("User de-registration", () -> {
                userManager.deregisterUser("luffy@onepiece.com");
            });

            benchmark("User de-registration", () -> {
                userManager.deregisterUser("luffy@onepiece.com");
            });

            benchmark("Log all users", () -> {
                userManager.logAllUsers();
            });

            userManager.shutdownLoggers();

            System.out.println("*".repeat(40) + "  END APP  " + "*".repeat(40));
        });
    }

    public static void benchmark(String name, Runnable runnable) {
        System.out.println("-".repeat(30) + " START BENCHMARK " + "-".repeat(30));
        long start = System.nanoTime();
        runnable.run();
        long end = System.nanoTime();
        System.out.println(name + ": " + (long) ((double) (end - start) / 1_000_000) + " ms");
        System.out.println("-".repeat(30) + "  END BENCHMARK  " + "-".repeat(30) + "\n");
    }
}
