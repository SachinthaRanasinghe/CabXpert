package com.mycompany.cabxpert_back.end;

import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

/**
 * REST Web Service - Login API
 *
 * @author sachintha
 */
@Path("Login")
public class LoginResource {

    @Context
    private UriInfo context;

    public LoginResource() {
    }

    /**
     * POST method for user authentication
     * @param content JSON string containing login credentials
     * @return Response indicating login success or failure
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(String content) {
        // Parse incoming JSON to extract login credentials
        Gson gson = new Gson();
        LoginCredentials loginCredentials = gson.fromJson(content, LoginCredentials.class);

        // Validate input
        if (loginCredentials.getUsername() == null || loginCredentials.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"message\":\"Missing required fields\"}").build();
        }

        // Validate user against database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String loginQuery = "SELECT username, role FROM User WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
                preparedStatement.setString(1, loginCredentials.getUsername());
                preparedStatement.setString(2, loginCredentials.getPassword());

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Retrieve role and send response
                    String role = resultSet.getString("role");
                    String responseMessage = "{\"message\":\"Login successful\", \"username\":\"" + resultSet.getString("username") + "\", \"role\":\"" + role + "\"}";
                    return Response.ok(responseMessage).build();
                } else {
                    // Invalid credentials
                    return Response.status(Response.Status.UNAUTHORIZED).entity("{\"message\":\"Invalid username or password\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    // Inner class to map login credentials from JSON
    class LoginCredentials {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
