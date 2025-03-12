package DriverDetails;

public class Driver {
    private int id;
    private String name;
    private String phoneNumber;
    private String licenseNumber;
    private String availability;
    private int vehicleId; // New field for VehicleID

    // Default constructor
    public Driver() {
    }

    // Constructor with parameters matching the expected format
    public Driver(int id, String name, String phoneNumber, String licenseNumber, String availability, int vehicleId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
        this.availability = availability;
        this.vehicleId = vehicleId; // Initialize VehicleID
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    // Getter and Setter for VehicleID
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}