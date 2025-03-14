package src.domain;

public class Vehicle {
    private String license;
    private VehicleType type;

    public Vehicle(String license, VehicleType type) {
        this.license = license;
        this.type = type;
    }

    public String getLicense() {
        return license;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "[license=" + license + ", type=" + type + "]";
    }

}
