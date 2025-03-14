package src.domain;

public class ParkingSpot {
    private final int level;
    private final String spotID;
    private final VehicleType vehicleType;
    private boolean availability;

    private ParkingSpot(int level, String spotID, VehicleType vehicleType) {
        this.level = level;
        this.spotID = spotID;
        this.vehicleType = vehicleType;
        this.availability = true;
    }

    public ParkingSpot(Builder builder) {
        this(builder.level, builder.spotID, builder.vehicleType);
    }

    public static class Builder {
        private int level;
        private String spotID;
        private VehicleType vehicleType;

        private Builder() {
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder spotID(String spotID) {
            this.spotID = spotID;
            return this;
        }

        public Builder vehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public ParkingSpot build() {
            return new ParkingSpot(this);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public int getLevel() {
        return level;
    }

    public String getSpotID() {
        return spotID;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "[spotID=" + spotID + ", vehicleType=" + vehicleType + "]";
    }

}
