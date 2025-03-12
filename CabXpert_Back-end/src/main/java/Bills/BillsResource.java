package Bills;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;

/**
 * REST Web Service for Billing
 *
 * Author: Sachintha Ranasinghe
 */
@Path("Bills")
public class BillsResource {

    @Context
    private UriInfo context;

    public BillsResource() {
    }

    /**
     * Retrieves bill details as JSON.
     *
     * @return JSON representation of bill details
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        // Mock response (Replace with database fetch logic)
        String jsonResponse = "{ \"message\": \"Bill details fetched successfully\" }";
        return Response.ok(jsonResponse).build();
    }

    /**
     * Updates or creates bill details
     *
     * @param content JSON representation of bill data
     * @return HTTP response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putJson(String content) {
        if (content == null || content.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid request data\"}")
                    .build();
        }

        // TODO: Implement logic to save/update bill details in the database
        System.out.println("Received bill data: " + content);

        return Response.ok("{\"message\": \"Bill details updated successfully\"}").build();
    }

    /**
     * Generates a bill for the given Booking ID
     *
     * @param bookingId JSON representation of the Booking ID
     * @return HTTP response with bill details
     */
    @POST
    @Path("/generateBill")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateBill(String bookingId) {
        Gson gson = new Gson();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Parse the Booking ID from the input JSON
            int id = gson.fromJson(bookingId, Integer.class);

            // Validate the Booking ID
            if (id <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Invalid Booking ID\"}")
                        .build();
            }

            // Generate a random total fare between Rs. 1000 and Rs. 10,000
            Random random = new Random();
            double totalFare = random.nextInt(9001) + 1000; // 1000 to 10000

            // Apply a discount of Rs. 500
            double discount = 500.00;
            double finalAmount = totalFare - discount;

            // Insert the bill details into the database
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Bill (BookingID, TotalFare, Discount, FinalAmount, PaymentStatus) "
                    + "VALUES (?, ?, ?, ?, 'Pending')";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setDouble(2, totalFare);
            pstmt.setDouble(3, discount);
            pstmt.setDouble(4, finalAmount);
            pstmt.executeUpdate();

            // Create the bill details JSON
            String jsonResponse = "{ \"bookingId\": " + id + ", "
                    + "\"totalFare\": " + totalFare + ", "
                    + "\"discount\": " + discount + ", "
                    + "\"finalAmount\": " + finalAmount + ", "
                    + "\"paymentStatus\": \"Pending\" }";

            return Response.ok(jsonResponse).build();
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            // Return a JSON error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } finally {
            // Close database resources
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves all bills excluding PaymentDate
     *
     * @return JSON representation of all bills
     */
    @GET
    @Path("/viewAllBills")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllBills() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Establish a connection to the database
            conn = DatabaseConnection.getConnection();

            // SQL query to fetch all bills excluding PaymentDate
            String sql = "SELECT BillID, BookingID, TotalFare, Tax, Discount, FinalAmount, PaymentStatus FROM Bill";

            // Prepare the statement
            pstmt = conn.prepareStatement(sql);

            // Execute the query
            rs = pstmt.executeQuery();

            // Create a JSON array to hold the bill details
            StringBuilder jsonResponse = new StringBuilder("[");
            boolean first = true;

            // Iterate through the result set and build the JSON response
            while (rs.next()) {
                if (!first) {
                    jsonResponse.append(",");
                } else {
                    first = false;
                }
                jsonResponse.append("{")
                        .append("\"BillID\": ").append(rs.getInt("BillID")).append(", ")
                        .append("\"BookingID\": ").append(rs.getInt("BookingID")).append(", ")
                        .append("\"TotalFare\": ").append(rs.getDouble("TotalFare")).append(", ")
                        .append("\"Tax\": ").append(rs.getDouble("Tax")).append(", ")
                        .append("\"Discount\": ").append(rs.getDouble("Discount")).append(", ")
                        .append("\"FinalAmount\": ").append(rs.getDouble("FinalAmount")).append(", ")
                        .append("\"PaymentStatus\": \"").append(rs.getString("PaymentStatus")).append("\"")
                        .append("}");
            }
            jsonResponse.append("]");

            // Return the JSON response
            return Response.ok(jsonResponse.toString()).build();
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            // Return a JSON error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } finally {
            // Close database resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the PaymentStatus of a specific bill
     *
     * @param billData JSON representation of the BillID and new PaymentStatus
     * @return HTTP response
     */
    @PUT
    @Path("/updatePaymentStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePaymentStatus(String billData) {
        Gson gson = new Gson();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Parse the input JSON
            BillUpdateRequest request = gson.fromJson(billData, BillUpdateRequest.class);

            // Validate the input
            if (request.getBillID() <= 0 || request.getPaymentStatus() == null || request.getPaymentStatus().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Invalid input data\"}")
                        .build();
            }

            // Update the PaymentStatus in the database
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE Bill SET PaymentStatus = ? WHERE BillID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, request.getPaymentStatus());
            pstmt.setInt(2, request.getBillID());
            int rowsUpdated = pstmt.executeUpdate();

            // Check if the update was successful
            if (rowsUpdated > 0) {
                return Response.ok("{\"message\": \"PaymentStatus updated successfully\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Bill not found\"}")
                        .build();
            }
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            // Return a JSON error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } finally {
            // Close database resources
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Inner class to represent the request body for updating PaymentStatus
     */
    private static class BillUpdateRequest {
        private int BillID;
        private String PaymentStatus;

        // Getters and Setters
        public int getBillID() {
            return BillID;
        }

        public void setBillID(int billID) {
            BillID = billID;
        }

        public String getPaymentStatus() {
            return PaymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            PaymentStatus = paymentStatus;
        }
    }
}