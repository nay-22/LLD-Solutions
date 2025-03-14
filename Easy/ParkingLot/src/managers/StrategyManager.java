package src.managers;

import src.domain.VehicleType;
import src.strategies.BikeFareCalculationStrategy;
import src.strategies.CarFareCalculationStrategy;
import src.strategies.PickupFareCalculationStrategy;
import src.strategies.interfaces.FareCalculationStrategy;

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
