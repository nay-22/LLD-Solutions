package src.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class Level {
    protected final int level;
    protected final Map<VehicleType, Queue<ParkingSpot>> availableSpots;
    protected final Map<String, ParkingSpot> reservedSpots;

    public Level(int level, int totalSpots) {
        this.level = level;
        this.availableSpots = new ConcurrentHashMap<>(totalSpots);

        for (VehicleType type : VehicleType.values()) {
            availableSpots.put(type, new LinkedList<>());
        }

        this.reservedSpots = new ConcurrentHashMap<>();

        int carSpots = (int) (0.60 * totalSpots);
        int bikeSpots = (int) (0.20 * totalSpots);
        int pickupSpots = (int) (0.20 * totalSpots);

        initializeSpots(VehicleType.CAR, carSpots);
        initializeSpots(VehicleType.BIKE, bikeSpots);
        initializeSpots(VehicleType.PICKUP, pickupSpots);
    }

    private void initializeSpots(VehicleType vehicleType, int count) {
        for (int i = 0; i < count; i++) {
            availableSpots.get(vehicleType).offer(
                    ParkingSpot.builder()
                            .level(level)
                            .spotID("L" + level + "-" + vehicleType.name() + "-" + i)
                            .vehicleType(vehicleType)
                            .build());
        }
    }

    public List<ParkingSpot> getAvailableSpots() {
        List<ParkingSpot> spots = new ArrayList<>();
        for (Queue<ParkingSpot> queue : availableSpots.values()) {
            spots.addAll(queue);
        }
        return spots;
    }

    public List<ParkingSpot> getReservedSpots() {
        return new ArrayList<>(reservedSpots.values());
    }

    public int availableSpotsSize() {
        return availableSpots.values().stream().mapToInt(Queue::size).sum();
    }

    public int reservedSpotsSize() {
        return reservedSpots.size();
    }

    public ParkingSpot reserve(VehicleType vehicleType) {
        if (availableSpots.get(vehicleType) == null || availableSpots.get(vehicleType).isEmpty()) {
            return null;
        }
        ParkingSpot spot = availableSpots.get(vehicleType).poll();
        reservedSpots.put(spot.getSpotID(), spot);
        spot.setAvailability(false);
        return spot;

    }

    public boolean free(ParkingSpot spot) {
        if (!reservedSpots.containsKey(spot.getSpotID())) {
            return false;
        }
        reservedSpots.remove(spot.getSpotID());
        availableSpots.get(spot.getVehicleType()).offer(spot);
        spot.setAvailability(true);
        return true;
    }

    @Override
    public String toString() {
        return "[level=" + level + ", availableSpots=" + availableSpots + ", reservedSpots=" + reservedSpots
                + "]";
    }

}
