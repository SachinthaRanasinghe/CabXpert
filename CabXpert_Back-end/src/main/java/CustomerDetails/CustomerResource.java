package CustomerDetails;

import com.google.gson.Gson;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import com.mycompany.cabxpert_back.end.DB.DatabaseConnection;
import java.util.ArrayList;
import java.util.List;

@Path("Customer")
public class CustomerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        String jsonResponse = "{ \"message\": \"Customer details fetched successfully\" }";
        return Response.ok(jsonResponse).build();
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(String content) {
        Gson gson = new Gson();
        Customer customer = gson.fromJson(content, Customer.class);

        if (customer.getName() == null || customer.getPhoneNumber() == null || customer.getAddress() == null || customer.getNic() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Missing required fields\"}").build();
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO Customer (Name, Address, NIC, PhoneNumber) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getAddress());
                preparedStatement.setString(3, customer.getNic());
                preparedStatement.setString(4, customer.getPhoneNumber());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return Response.status(Response.Status.CREATED)
                            .entity("{\"message\": \"Customer added successfully\"}").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"message\": \"Failed to add customer\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Customer";

            // Prepare the query statement
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // List to hold all customers
                List<Customer> customers = new ArrayList<>();

                // Iterate through the result set and create Customer objects
                while (resultSet.next()) {
                    // Get the RegistrationDate as a Timestamp
                    Timestamp timestamp = resultSet.getTimestamp("RegistrationDate");
                    String registrationDate = (timestamp != null) ? timestamp.toString() : null;

                    // Create the customer object and add it to the list
                    Customer customer = new Customer(
                            resultSet.getInt("CustomerID"),
                            resultSet.getString("Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("NIC"),
                            resultSet.getString("PhoneNumber"),
                            registrationDate // Store the formatted date string
                    );
                    customers.add(customer);
                }

                // If customers were found, return them in JSON format
                if (!customers.isEmpty()) {
                    return Response.ok(new Gson().toJson(customers)).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"No customers found\"}")
                            .build();
                }
            }
        } catch (SQLException e) {
            // Log and return database error message
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, String content) {
        Gson gson = new Gson();
        Customer customer = gson.fromJson(content, Customer.class);

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch current customer data
            String selectQuery = "SELECT * FROM Customer WHERE CustomerID = ?";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setInt(1, id);
                ResultSet resultSet = selectStmt.executeQuery();

                if (!resultSet.next()) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity("{\"message\": \"Customer not found\"}").build();
                }

                // Preserve existing values if not provided in request
                String currentName = resultSet.getString("Name");
                String currentPhoneNumber = resultSet.getString("PhoneNumber");
                String currentNic = resultSet.getString("NIC");
                String currentAddress = resultSet.getString("Address");

                String newName = (customer.getName() != null) ? customer.getName() : currentName;
                String newPhoneNumber = (customer.getPhoneNumber() != null) ? customer.getPhoneNumber() : currentPhoneNumber;
                String newNic = (customer.getNic() != null) ? customer.getNic() : currentNic;
                String newAddress = (customer.getAddress() != null) ? customer.getAddress() : currentAddress;

                // Update only provided fields
                String updateQuery = "UPDATE Customer SET Name = ?, PhoneNumber = ?, NIC = ?, Address = ? WHERE CustomerID = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newName);
                    updateStmt.setString(2, newPhoneNumber);
                    updateStmt.setString(3, newNic);
                    updateStmt.setString(4, newAddress);
                    updateStmt.setInt(5, id);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        return Response.ok("{\"message\": \"Customer updated successfully\"}").build();
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

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") int id) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String deleteQuery = "DELETE FROM Customer WHERE CustomerID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    return Response.ok("{\"message\": \"Customer deleted successfully\"}").build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("{\"message\": \"Customer not found\"}").build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Database error: " + e.getMessage() + "\"}").build();
        }
    }
}
