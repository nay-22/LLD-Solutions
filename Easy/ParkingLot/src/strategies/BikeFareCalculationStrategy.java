package Easy.ParkingLot.src.strategies;

import Easy.ParkingLot.src.strategies.interfaces.FareCalculationStrategy;

public class BikeFareCalculationStrategy implements FareCalculationStrategy {

    @Override
    public double calculateFare(int mins) {
        return Math.max(1, Math.ceil((double) mins / 60)) * 20;
    }

}
