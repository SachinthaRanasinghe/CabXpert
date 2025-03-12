package Vehicle;

public class Vehicle {
    private int vehicleID;
    private String licensePlate;
    private String model;
    private int capacity;
    private String status;

    // Updated constructor without DriverID
    public Vehicle(int vehicleID, String licensePlate, String model, int capacity, String status) {
        this.vehicleID = vehicleID;
        this.licensePlate = licensePlate;
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }

    // Getters and setters
    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}