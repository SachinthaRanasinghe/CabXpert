package Bookings;  // Ensure the correct package name

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Booking {
    private int bookingID;
    private int customerID;
    private String pickupLocation;
    private String destination;
    private Timestamp bookingDateTime;
    private String status;
    private BigDecimal fare;
    private int driverID;

    // Constructor
    public Booking(int bookingID, int customerID, String pickupLocation, String destination, 
                   Timestamp bookingDateTime, String status, BigDecimal fare, int driverID) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        this.bookingDateTime = bookingDateTime;
        this.status = status;
        this.fare = fare;
        this.driverID = driverID;
    }

    // Getters
    public int getBookingID() { return bookingID; }
    public int getCustomerID() { return customerID; }
    public String getPickupLocation() { return pickupLocation; }
    public String getDestination() { return destination; }
    public Timestamp getBookingDateTime() { return bookingDateTime; }
    public String getStatus() { return status; }
    public BigDecimal getFare() { return fare; }
    public int getDriverID() { return driverID; }
}
