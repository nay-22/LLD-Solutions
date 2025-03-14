package src.managers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.domain.Level;
import src.domain.ParkingSpot;
import src.domain.Ticket;
import src.domain.Vehicle;
import src.exceptions.ParkingException;

public class ParkingManager {
    private static class SingletonHelper {
        private static final ParkingManager MANAGER = new ParkingManager();
    }

    public static final int DEFAULT_SPOTS = 30;
    private final StrategyManager strategyManager;
    private final List<Level> levels;
    private final Set<Vehicle> parkedCars;

    private ParkingManager() {
        this.parkedCars = new HashSet<>();
        this.levels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.levels.add(new Level(i, DEFAULT_SPOTS));
        }
        this.strategyManager = StrategyManager.getManager();
    }

    public static ParkingManager getManager() {
        return SingletonHelper.MANAGER;
    }

    public synchronized Ticket park(Vehicle vehicle) throws ParkingException {
        if (parkedCars.contains(vehicle)) {
            throw new ParkingException("The vehicle " + vehicle.getLicense() + " has already been parked!");
        }
        for (Level level : levels) {
            ParkingSpot spot = level.reserve(vehicle.getType());
            if (spot == null) {
                continue;
            }
            try {
                Ticket ticket = new Ticket(spot, vehicle,
                        strategyManager.getFareCalculationStrategy(vehicle.getType()));
                parkedCars.add(vehicle);
                return ticket;
            } catch (Exception e) {
                level.free(spot);
                throw new ParkingException("Error occurred while creating ticket or parking vehicle");
            }
        }
        throw new ParkingException("All parking spaces are full!");
    }

    public synchronized void unpark(Ticket ticket, int mins) throws ParkingException {
        if (!levels.get(ticket.getSpot().getLevel()).free(ticket.getSpot())) {
            throw new ParkingException("The vehicle " + ticket.getVehicle().getLicense() + " has already exited!");
        }
        parkedCars.remove(ticket.getVehicle());
        ticket.setFare(mins);
    }

    public List<Vehicle> getParkedVehicles() {
        return List.copyOf(parkedCars);
    }
}
