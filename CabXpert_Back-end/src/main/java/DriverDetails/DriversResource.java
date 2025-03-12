package DriverDetails;

import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;

/**
 * REST Web Service for Driver Details Handles fetching, adding, updating, and
 * deleting driver data.
 *
 * @author Sachintha
 */
@Path("Drivers")
public class DriversResource {

    @Context
    private UriInfo context;

    private static final Gson gson = new Gson();

    /**
     * Retrieves a message confirming the endpoint is available.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"message\": \"Driver details endpoint\"}";
    }

    /**
     * Adds a new driver to the database.
     */
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDriver(String content) {
        Driver driver = gson.fromJson(content, Driver.class);

        // Validate input fields
        if (driver.getName() == null || driver.getPhoneNumber() == null || driver.getLicenseNumber() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Missing required fields\"}").build();
        }

        // Insert driver into database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO Driver (Name, PhoneNumber, LicenseNumber, Availability, VehicleID) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, driver.getName());
                preparedStatement.setString(2, driver.getPhoneNumber());
                preparedStatement.setString(3, driver.getLicenseNumber());
                preparedStatement.setString(4, driver.getAvailability() != null ? driver.getAvailability() : "Available");
                preparedStatement.setInt(5, driver.getVehicleId()); // Add the VehicleID

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.CREATED)
                            .entity("{\"message\": \"Driver added successfully\"}").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"message\": \"Failed to add driver\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Retrieves a driver by ID.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriverById(@PathParam("id") int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Driver WHERE DriverID = ?";  // Ensure column name matches DB
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Create Driver object with constructor matching the DB columns
                    Driver driver = new Driver(
                            resultSet.getInt("DriverID"),
                            resultSet.getString("Name"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("LicenseNumber"),
                            resultSet.getString("Availability"),
                            resultSet.getInt("VehicleID") // Include VehicleID
                    );
                    return Response.ok(gson.toJson(driver)).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Driver not found\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDrivers() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Driver";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

                List<Driver> drivers = new ArrayList<>();
                while (resultSet.next()) {
                    Driver driver = new Driver(
                            resultSet.getInt("DriverID"),
                            resultSet.getString("Name"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("LicenseNumber"),
                            resultSet.getString("Availability"),
                            resultSet.getInt("VehicleID") // Include VehicleID
                    );
                    drivers.add(driver);
                }
                return Response.ok(gson.toJson(drivers)).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDriver(@PathParam("id") int id, String content) {
        Driver driver = gson.fromJson(content, Driver.class);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch current driver data
            String selectQuery = "SELECT * FROM Driver WHERE DriverID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, id);
                ResultSet resultSet = selectStmt.executeQuery();

                if (!resultSet.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Driver not found\"}").build();
                }

                // Preserve existing values if not provided in request
                String currentName = resultSet.getString("Name");
                String currentPhoneNumber = resultSet.getString("PhoneNumber");
                String currentLicenseNumber = resultSet.getString("LicenseNumber");
                String currentAvailability = resultSet.getString("Availability");
                int currentVehicleId = resultSet.getInt("VehicleID");

                String newName = (driver.getName() != null) ? driver.getName() : currentName;
                String newPhoneNumber = (driver.getPhoneNumber() != null) ? driver.getPhoneNumber() : currentPhoneNumber;
                String newLicenseNumber = (driver.getLicenseNumber() != null) ? driver.getLicenseNumber() : currentLicenseNumber;
                String newAvailability = (driver.getAvailability() != null) ? driver.getAvailability() : currentAvailability;
                int newVehicleId = (driver.getVehicleId() != 0) ? driver.getVehicleId() : currentVehicleId;

                // Update only provided fields
                String updateQuery = "UPDATE Driver SET Name = ?, PhoneNumber = ?, LicenseNumber = ?, Availability = ?, VehicleID = ? WHERE DriverID = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newName);
                    updateStmt.setString(2, newPhoneNumber);
                    updateStmt.setString(3, newLicenseNumber);
                    updateStmt.setString(4, newAvailability);
                    updateStmt.setInt(5, newVehicleId); // Update VehicleID
                    updateStmt.setInt(6, id);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        return Response.ok("{\"message\": \"Driver updated successfully\"}").build();
                    } else {
                        return Response.status(Response.Status.NOT_MODIFIED)
                                .entity("{\"message\": \"No changes made\"}").build();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Deletes a driver by ID.
     */
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDriver(@PathParam("id") int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String deleteQuery = "DELETE FROM Driver WHERE DriverID = ?";  // Changed 'ID' to 'DriverID'

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    return Response.ok("{\"message\": \"Driver deleted successfully\"}").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Driver not found\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Retrieves all vehicle IDs from the Vehicle table.
     */
    @GET
    @Path("vehicles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllVehicleIds() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT VehicleID FROM Vehicle";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); 
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                List<Integer> vehicleIds = new ArrayList<>();
                while (resultSet.next()) {
                    vehicleIds.add(resultSet.getInt("VehicleID"));
                }
                return Response.ok(gson.toJson(vehicleIds)).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }
}