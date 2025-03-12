package Bookings;

import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;
import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * REST Web Service for Viewing Bookings
 * Author: Sachintha
 */
@Path("ViewBookings")
public class ViewBookingsResource {

    /**
     * Retrieves all bookings from the database in JSON format.
     * @return JSON response with booking details
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT BookingID, CustomerID, PickupLocation, Destination, BookingDateTime, Status, Fare, DriverID FROM Booking";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("CustomerID"),
                    rs.getString("PickupLocation"),
                    rs.getString("Destination"),
                    rs.getTimestamp("BookingDateTime"),
                    rs.getString("Status"),
                    rs.getBigDecimal("Fare"),
                    rs.getInt("DriverID")
                ));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"error\": \"SQL error: " + sqlEx.getMessage() + "\" }")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"error\": \"Database error: " + e.getMessage() + "\" }")
                    .build();
        }
        return Response.ok(new Gson().toJson(bookings)).build();
    }
}