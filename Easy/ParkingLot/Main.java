package Easy.ParkingLot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import Easy.ParkingLot.src.managers.ParkingManager;
import Easy.ParkingLot.src.domain.VehicleType;
import Easy.ParkingLot.src.domain.Vehicle;
import Easy.ParkingLot.src.domain.Ticket;

public class Main {
    private static final int NUM_VEHICLES = 100;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        ParkingManager parkingManager = ParkingManager.getManager();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Vehicle> vehicles = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < NUM_VEHICLES; i++) {
            vehicles.add(new Vehicle("MH" + (10 + i) + "XYZ" + RANDOM.nextInt(9999), getRandomVehicleType()));
        }

        vehicles.forEach(System.out::println);

        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }

        // Simulate concurrent parking
        vehicles.forEach(vehicle -> executorService.submit(() -> {
            try {
                Ticket ticket = parkingManager.park(vehicle);
                synchronized (tickets) {
                    tickets.add(ticket);
                }
                System.out.println(vehicle.getLicense() + " parked.");
            } catch (Exception e) {
                System.err.println("Failed to park " + vehicle.getLicense() + ": " + e.getMessage());
            }
        }));

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        System.out.println("\nTotal Parked Vehicles: " + parkingManager.getParkedVehicles().size());

        ExecutorService unparkExecutor = Executors.newFixedThreadPool(10);
        tickets.forEach(ticket -> unparkExecutor.submit(() -> {
            try {
                int duration = RANDOM.nextInt(120) + 1;
                parkingManager.unpark(ticket, duration);
                System.out.println(ticket.getVehicle().getLicense() + " unparked after " + duration + " mins.");
            } catch (Exception e) {
                System.err.println("Failed to unpark: " + e.getMessage());
            }
        }));

        unparkExecutor.shutdown();
        while (!unparkExecutor.isTerminated()) {
        }

        System.out.println("\nFinal Parked Vehicles: " + parkingManager.getParkedVehicles().size());

        tickets.forEach(System.out::println);
    }

    private static VehicleType getRandomVehicleType() {
        VehicleType[] types = VehicleType.values();
        return types[RANDOM.nextInt(types.length)];
    }
}
