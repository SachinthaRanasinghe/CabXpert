package com.mycompany.cabxpert_back.end;

import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import com.google.gson.Gson;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * REST Web Service for managing users
 */
@Path("user")
public class UserResource {

    /**
     * Retrieves all users from the database in JSON format.
     *
     * @return JSON representation of user list
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT UserID, Username, FullName, Email, PhoneNumber, Address, NIC, Role, CreatedAt FROM User";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(new User(
                    rs.getInt("UserID"),
                    rs.getString("Username"),
                    rs.getString("FullName"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Address"),
                    rs.getString("NIC"),
                    rs.getString("Role"),
                    rs.getTimestamp("CreatedAt")
                ));
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return "{\"error\":\"SQL error - SQLState: " + sqlEx.getSQLState() +
                   ", ErrorCode: " + sqlEx.getErrorCode() + ", Message: " + sqlEx.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            String errorDetail = "Exception type: " + e.getClass().getName() + ", Message: " + e.getMessage();
            if (e.getCause() != null) {
                errorDetail += ", Caused by: " + e.getCause().toString();
            }
            return "{\"error\":\"Database error: " + errorDetail + "\"}";
        }
        return new Gson().toJson(users);
    }

    /**
     * Adds a new user to the database.
     *
     * @param jsonData JSON data of the user
     * @return Response status
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addUser(String jsonData) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);

        String sql = "INSERT INTO User (Username, Password, FullName, Email, PhoneNumber, Address, NIC, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Ensure this is properly hashed if needed
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getAddress());
            stmt.setString(7, user.getNic());
            stmt.setString(8, user.getRole());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return "{\"status\": \"success\", \"message\": \"User added successfully\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"Failed to add user\"}";
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return "{\"error\":\"SQL error - SQLState: " + sqlEx.getSQLState() +
                   ", ErrorCode: " + sqlEx.getErrorCode() + ", Message: " + sqlEx.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            String errorDetail = "Exception type: " + e.getClass().getName() + ", Message: " + e.getMessage();
            if (e.getCause() != null) {
                errorDetail += ", Caused by: " + e.getCause().toString();
            }
            return "{\"error\":\"Database error: " + errorDetail + "\"}";
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param userId   The ID of the user to be updated
     * @param jsonData JSON data containing the updated user details
     * @return Response status
     */
    @PUT
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateUser(@PathParam("userId") int userId, String jsonData) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);

        String sql = "UPDATE User SET Username = ?, FullName = ?, Email = ?, PhoneNumber = ?, Address = ?, NIC = ?, Role = ? WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getNic());
            stmt.setString(7, user.getRole());
            stmt.setInt(8, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "{\"status\": \"success\", \"message\": \"User updated successfully\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"Failed to update user\"}";
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return "{\"error\":\"SQL error - SQLState: " + sqlEx.getSQLState() +
                   ", ErrorCode: " + sqlEx.getErrorCode() + ", Message: " + sqlEx.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            String errorDetail = "Exception type: " + e.getClass().getName() + ", Message: " + e.getMessage();
            if (e.getCause() != null) {
                errorDetail += ", Caused by: " + e.getCause().toString();
            }
            return "{\"error\":\"Database error: " + errorDetail + "\"}";
        }
    }

    /**
     * Deletes a user from the database by UserID.
     *
     * @param userId The ID of the user to be deleted
     * @return Response status
     */
    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("userId") int userId) {
        String sql = "DELETE FROM User WHERE UserID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsDeleted = stmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                return "{\"status\": \"success\", \"message\": \"User deleted successfully\"}";
            } else {
                return "{\"status\": \"failure\", \"message\": \"User not found\"}";
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return "{\"error\":\"SQL error - SQLState: " + sqlEx.getSQLState() +
                   ", ErrorCode: " + sqlEx.getErrorCode() + ", Message: " + sqlEx.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            String errorDetail = "Exception type: " + e.getClass().getName() + ", Message: " + e.getMessage();
            if (e.getCause() != null) {
                errorDetail += ", Caused by: " + e.getCause().toString();
            }
            return "{\"error\":\"Database error: " + errorDetail + "\"}";
        }
    }
}
