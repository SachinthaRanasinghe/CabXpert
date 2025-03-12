package com.mycompany.cabxpert_back.end;

import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.google.gson.Gson;

@Path("Register")
public class RegisterResource {

    @Context
    private UriInfo context;

    public RegisterResource() {
    }

    // Existing POST method for user registration
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(String content) {
        Gson gson = new Gson();
        User user = gson.fromJson(content, User.class);

        // Validate required fields
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"Missing required fields\"}").build();
        }

        // Insert user into the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertUserQuery = "INSERT INTO User (Username, Password, FullName, Email, PhoneNumber, Address, NIC, Role, CreatedAt) " +
                                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getFullName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getPhoneNumber());
                preparedStatement.setString(6, user.getAddress());
                preparedStatement.setString(7, user.getNic());
                preparedStatement.setString(8, user.getRole());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.CREATED).entity("{\"message\":\"Registration successful\"}").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Failed to register user\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    // New DELETE method for user deletion
    @DELETE
    @Path("/{UserID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("UserID") int UserID) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String deleteUserQuery = "DELETE FROM User WHERE UserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery)) {
                preparedStatement.setInt(1, UserID);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.OK).entity("{\"message\":\"User deleted successfully\"}").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"User not found\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    // New PUT method for user updates
    @PUT
    @Path("/{UserID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("UserID") int UserID, String content) {
        Gson gson = new Gson();
        User user = gson.fromJson(content, User.class);

        // Validate required fields
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"Missing required fields\"}").build();
        }

        // Update user in the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String updateUserQuery = "UPDATE User SET Username = ?, Password = ?, FullName = ?, Email = ?, PhoneNumber = ?, Address = ?, NIC = ?, Role = ? WHERE UserID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getFullName());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setString(5, user.getPhoneNumber());
                preparedStatement.setString(6, user.getAddress());
                preparedStatement.setString(7, user.getNic());
                preparedStatement.setString(8, user.getRole());
                preparedStatement.setInt(9, UserID);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.OK).entity("{\"message\":\"User updated successfully\"}").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"User not found\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    // New GET method to retrieve all users
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"SQL error - SQLState: " + sqlEx.getSQLState() +
                                   ", ErrorCode: " + sqlEx.getErrorCode() + ", Message: " + sqlEx.getMessage() + "\"}")
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            String errorDetail = "Exception type: " + e.getClass().getName() + ", Message: " + e.getMessage();
            if (e.getCause() != null) {
                errorDetail += ", Caused by: " + e.getCause().toString();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"Database error: " + errorDetail + "\"}")
                           .build();
        }
        return Response.status(Response.Status.OK).entity(new Gson().toJson(users)).build();
    }
}