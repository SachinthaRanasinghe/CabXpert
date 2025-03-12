package Bookings;

import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * REST Web Service for Adding and Deleting Bookings
 *
 * @author sachintha
 */
@Path("AddBooking")
public class AddBookingResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AddBookingResource
     */
    public AddBookingResource() {
    }

    /**
     * Handles GET requests (for testing purposes).
     * Returns a simple message to confirm the service is running.
     *
     * @return A JSON response with a status message.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        String responseMessage = "{\"status\": \"AddBooking service is running\"}";
        return Response.status(Response.Status.OK).entity(responseMessage).build();
    }

    /**
     * POST method for adding a new booking
     * @param content JSON string representing the booking details
     * @return Response indicating whether the booking was successful
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(String content) {
        JSONObject json = new JSONObject(content);
        String nic = json.optString("customerNIC");
        String pickupLocation = json.optString("pickupLocation");
        String destination = json.optString("destination");
        double fare = json.optDouble("fare", 0.00);
        int customerID = json.optInt("customerID", -1);
        int driverID = json.optInt("driverID", -1);

        if (pickupLocation.isEmpty() || destination.isEmpty() || fare <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing required fields").build();
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (customerID == -1) {
                // Retrieve CustomerID if not provided
                String customerQuery = "SELECT CustomerID FROM Customer WHERE NIC = ?";
                try (PreparedStatement psCustomer = connection.prepareStatement(customerQuery)) {
                    psCustomer.setString(1, nic);
                    try (ResultSet rs = psCustomer.executeQuery()) {
                        if (rs.next()) {
                            customerID = rs.getInt("CustomerID");
                        } else {
                            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found").build();
                        }
                    }
                }
            }
            
            if (driverID == -1) {
                // Retrieve Available DriverID if not provided
                String driverQuery = "SELECT DriverID FROM Driver WHERE Availability = 'Available' LIMIT 1";
                try (PreparedStatement psDriver = connection.prepareStatement(driverQuery);
                     ResultSet rs = psDriver.executeQuery()) {
                    if (rs.next()) {
                        driverID = rs.getInt("DriverID");
                    } else {
                        return Response.status(Response.Status.NOT_FOUND).entity("No available drivers").build();
                    }
                }
            }

            // Insert booking details
            String insertBookingQuery = "INSERT INTO Booking (CustomerID, PickupLocation, Destination, BookingDateTime, Status, Fare, DriverID) " +
                                       "VALUES (?, ?, ?, NOW(), 'Pending', ?, ?)";
            try (PreparedStatement psBooking = connection.prepareStatement(insertBookingQuery)) {
                psBooking.setInt(1, customerID);
                psBooking.setString(2, pickupLocation);
                psBooking.setString(3, destination);
                psBooking.setDouble(4, fare);
                psBooking.setInt(5, driverID);
                
                int rowsAffected = psBooking.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.CREATED).entity("{\"message\":\"Booking successful\"}").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Failed to add booking\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("getCustomerAndDriver")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerAndDriver() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Retrieve customer IDs
            String customerQuery = "SELECT CustomerID, NIC FROM Customer";
            JSONArray customerArray = new JSONArray();
            try (PreparedStatement psCustomer = connection.prepareStatement(customerQuery);
                 ResultSet rsCustomer = psCustomer.executeQuery()) {
                while (rsCustomer.next()) {
                    JSONObject customer = new JSONObject();
                    customer.put("CustomerID", rsCustomer.getInt("CustomerID"));
                    customer.put("NIC", rsCustomer.getString("NIC"));
                    customerArray.put(customer);
                }
            }

            // Retrieve available driver IDs
            String driverQuery = "SELECT DriverID, Name FROM Driver WHERE Availability = 'Available'";
            JSONArray driverArray = new JSONArray();
            try (PreparedStatement psDriver = connection.prepareStatement(driverQuery);
                 ResultSet rsDriver = psDriver.executeQuery()) {
                while (rsDriver.next()) {
                    JSONObject driver = new JSONObject();
                    driver.put("DriverID", rsDriver.getInt("DriverID"));
                    driver.put("Name", rsDriver.getString("Name"));
                    driverArray.put(driver);
                }
            }

            JSONObject response = new JSONObject();
            response.put("customers", customerArray);
            response.put("drivers", driverArray);

            return Response.status(Response.Status.OK).entity(response.toString()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Error fetching data\"}").build();
        }
    }

    /**
     * DELETE method for deleting a booking by BookingID
     * @param bookingID The ID of the booking to delete
     * @return Response indicating whether the deletion was successful
     */
    @DELETE
@Path("Bills/deleteByBookingID/{bookingID}")
@Produces(MediaType.APPLICATION_JSON)
public Response deleteBillsByBookingID(@PathParam("bookingID") int bookingID) {
    try (Connection connection = DatabaseConnection.getConnection()) {
        // Delete bills associated with the booking
        String deleteBillsQuery = "DELETE FROM bill WHERE BookingID = ?";
        try (PreparedStatement ps = connection.prepareStatement(deleteBillsQuery)) {
            ps.setInt(1, bookingID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return Response.status(Response.Status.OK).entity("{\"message\":\"Related bills deleted successfully\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"No bills found for this booking\"}").build();
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Database error: " + e.getMessage() + "\"}").build();
    }
}
}