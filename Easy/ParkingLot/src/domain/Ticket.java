package src.domain;

import src.strategies.interfaces.FareCalculationStrategy;

public class Ticket {
    private ParkingSpot spot;
    private Vehicle vehicle;
    private double fare;
    private FareCalculationStrategy strategy;

    public Ticket(ParkingSpot spot, Vehicle vehicle, FareCalculationStrategy strategy) {
        this.spot = spot;
        this.vehicle = vehicle;
        this.strategy = strategy;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(int mins) {
        this.fare = strategy.calculateFare(mins);
    }

    @Override
    public String toString() {
        return "Ticket [spotId=" + spot + ", vehicle=" + vehicle + ", fare=" + fare + "]";
    }

}
