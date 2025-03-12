package Vehicle;

import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;

/**
 * REST Web Service for Vehicle Details
 * Handles fetching, adding, updating, and deleting vehicle data.
 *
 * @author Sachintha
 */
@Path("Vehicles")
public class VehicleResource {

    @Context
    private UriInfo context;

    private static final Gson gson = new Gson();

    /**
     * Retrieves a message confirming the endpoint is available.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"message\": \"Vehicle details endpoint\"}";
    }

    /**
     * Adds a new vehicle to the database.
     */
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVehicle(String content) {
        Vehicle vehicle = gson.fromJson(content, Vehicle.class);

        // Validate input fields
        if (vehicle.getLicensePlate() == null || vehicle.getModel() == null || vehicle.getCapacity() == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Missing required fields\"}").build();
        }

        // Insert vehicle into database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO Vehicle (LicensePlate, Model, Capacity, Status) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, vehicle.getLicensePlate());
                preparedStatement.setString(2, vehicle.getModel());
                preparedStatement.setInt(3, vehicle.getCapacity());
                preparedStatement.setString(4, vehicle.getStatus() != null ? vehicle.getStatus() : "Available");

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.CREATED)
                            .entity("{\"message\": \"Vehicle added successfully\"}").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"message\": \"Failed to add vehicle\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    /**
     * Retrieves a vehicle by ID.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehicleById(@PathParam("id") int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Vehicle WHERE VehicleID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    Vehicle vehicle = new Vehicle(
                            resultSet.getInt("VehicleID"),
                            resultSet.getString("LicensePlate"),
                            resultSet.getString("Model"),
                            resultSet.getInt("Capacity"),
                            resultSet.getString("Status")
                    );
                    return Response.ok(gson.toJson(vehicle)).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Vehicle not found\"}").build();
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
    public Response getAllVehicles() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Vehicle";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

                List<Vehicle> vehicles = new ArrayList<>();
                while (resultSet.next()) {
                    Vehicle vehicle = new Vehicle(
                            resultSet.getInt("VehicleID"),
                            resultSet.getString("LicensePlate"),
                            resultSet.getString("Model"),
                            resultSet.getInt("Capacity"),
                            resultSet.getString("Status")
                    );
                    vehicles.add(vehicle);
                }
                return Response.ok(gson.toJson(vehicles)).build();
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
    public Response updateVehicle(@PathParam("id") int id, String content) {
        Vehicle vehicle = gson.fromJson(content, Vehicle.class);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch current vehicle data
            String selectQuery = "SELECT * FROM Vehicle WHERE VehicleID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, id);
                ResultSet resultSet = selectStmt.executeQuery();

                if (!resultSet.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Vehicle not found\"}").build();
                }

                // Preserve existing values if not provided in request
                String currentLicensePlate = resultSet.getString("LicensePlate");
                String currentModel = resultSet.getString("Model");
                int currentCapacity = resultSet.getInt("Capacity");
                String currentStatus = resultSet.getString("Status");

                String newLicensePlate = (vehicle.getLicensePlate() != null) ? vehicle.getLicensePlate() : currentLicensePlate;
                String newModel = (vehicle.getModel() != null) ? vehicle.getModel() : currentModel;
                int newCapacity = (vehicle.getCapacity() != 0) ? vehicle.getCapacity() : currentCapacity;
                String newStatus = (vehicle.getStatus() != null) ? vehicle.getStatus() : currentStatus;

                // Update only provided fields
                String updateQuery = "UPDATE Vehicle SET LicensePlate = ?, Model = ?, Capacity = ?, Status = ? WHERE VehicleID = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newLicensePlate);
                    updateStmt.setString(2, newModel);
                    updateStmt.setInt(3, newCapacity);
                    updateStmt.setString(4, newStatus);
                    updateStmt.setInt(5, id);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        return Response.ok("{\"message\": \"Vehicle updated successfully\"}").build();
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
     * Deletes a vehicle by ID.
     */
    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteVehicle(@PathParam("id") int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String deleteQuery = "DELETE FROM Vehicle WHERE VehicleID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    return Response.ok("{\"message\": \"Vehicle deleted successfully\"}").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Vehicle not found\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }
}