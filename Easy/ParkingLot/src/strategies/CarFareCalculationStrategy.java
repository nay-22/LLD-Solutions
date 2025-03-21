package Easy.ParkingLot.src.strategies;

import Easy.ParkingLot.src.strategies.interfaces.FareCalculationStrategy;

public class CarFareCalculationStrategy implements FareCalculationStrategy {

    @Override
    public double calculateFare(int mins) {
        return Math.max(1, Math.ceil((double) mins / 60)) * 40;
    }

}
