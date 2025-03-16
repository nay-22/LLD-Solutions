package Easy.ParkingLot.src.managers;

import Easy.ParkingLot.src.strategies.interfaces.FareCalculationStrategy;
import Easy.ParkingLot.src.strategies.PickupFareCalculationStrategy;
import Easy.ParkingLot.src.strategies.BikeFareCalculationStrategy;
import Easy.ParkingLot.src.strategies.CarFareCalculationStrategy;
import Easy.ParkingLot.src.domain.VehicleType;

public class StrategyManager {
    private static class SingletonHelper {
        private static final StrategyManager MANAGER = new StrategyManager();
    }

    private StrategyManager() {
    }

    public static StrategyManager getManager() {
        return SingletonHelper.MANAGER;
    }

    public FareCalculationStrategy getFareCalculationStrategy(VehicleType vehicleType) {
        return switch (vehicleType) {
            case BIKE -> new BikeFareCalculationStrategy();
            case CAR -> new CarFareCalculationStrategy();
            case PICKUP -> new PickupFareCalculationStrategy();
        };
    }
}
