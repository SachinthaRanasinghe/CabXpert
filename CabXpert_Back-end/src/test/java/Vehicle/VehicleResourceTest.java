//package Vehicle;
//
//import javax.ws.rs.core.Response;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author sachintha
// */
//public class VehicleResourceTest {
//
//    public VehicleResourceTest() {
//    }
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize any resources or setup required before each test
//    }
//
//    @AfterEach
//    public void tearDown() {
//        // Clean up resources after each test
//    }
//
//    /**
//     * Test of getJson method, of class VehicleResource.
//     */
//    @Test
//    public void testGetJson() {
//        System.out.println("getJson");
//        VehicleResource instance = new VehicleResource();
//        
//        // Test the getJson method
//        String result = instance.getJson();
//        assertNotNull(result); // Ensure the result is not null
//        assertFalse(result.isEmpty()); // Ensure the result is not empty
//    }
//
//    /**
//     * Test of addVehicle method, of class VehicleResource.
//     */
//    @Test
//    public void testAddVehicle() {
//        System.out.println("addVehicle");
//        VehicleResource instance = new VehicleResource();
//        
//        // Create a new vehicle JSON string
//        String content = "{\"licensePlate\": \"ABC-128\", \"model\": \"Toyota Corolla\", \"capacity\": 4, \"status\": \"Available\"}";
//        
//        // Test the addVehicle method
//        Response result = instance.addVehicle(content);
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus()); // Ensure the status code is 201 (Created)
//    }
//
//    /**
//     * Test of getVehicleById method, of class VehicleResource.
//     */
//    @Test
//    public void testGetVehicleById() {
//        System.out.println("getVehicleById");
//        VehicleResource instance = new VehicleResource();
//        
//        // Add a vehicle first
//        String addContent = "{\"licensePlate\": \"ABC-123\", \"model\": \"Toyota Corolla\", \"capacity\": 4, \"status\": \"Available\"}";
//        Response addResponse = instance.addVehicle(addContent);
//        assertNotNull(addResponse); // Ensure the vehicle was added
//        
//        // Test the getVehicleById method
//        int id = 1; // Assuming the vehicle ID is 1
//        Response result = instance.getVehicleById(id);
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of getAllVehicles method, of class VehicleResource.
//     */
//    @Test
//    public void testGetAllVehicles() {
//        System.out.println("getAllVehicles");
//        VehicleResource instance = new VehicleResource();
//        
//        // Test the getAllVehicles method
//        Response result = instance.getAllVehicles();
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of updateVehicle method, of class VehicleResource.
//     */
//    @Test
//    public void testUpdateVehicle() {
//        System.out.println("updateVehicle");
//        VehicleResource instance = new VehicleResource();
//        
//        // Add a vehicle first
////        String addContent = "{\"licensePlate\": \"ABC-123\", \"model\": \"Toyota Corolla\", \"capacity\": 4, \"status\": \"Available\"}";
////        Response addResponse = instance.addVehicle(addContent);
////        assertNotNull(addResponse); // Ensure the vehicle was added
//        
//        // Update the vehicle
//        int id = 4; // Assuming the vehicle ID is 1
//        String updateContent = "{\"licensePlate\": \"XYZ-789\", \"model\": \"Honda Civic\", \"capacity\": 5, \"status\": \"In Use\"}";
//        Response updateResponse = instance.updateVehicle(id, updateContent);
//        assertNotNull(updateResponse); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of deleteVehicle method, of class VehicleResource.
//     */
//    @Test
//    public void testDeleteVehicle() {
//        System.out.println("deleteVehicle");
//        VehicleResource instance = new VehicleResource();
//        
////        // Add a vehicle first
////        String addContent = "{\"licensePlate\": \"ABC-123\", \"model\": \"Toyota Corolla\", \"capacity\": 4, \"status\": \"Available\"}";
////        Response addResponse = instance.addVehicle(addContent);
////        assertNotNull(addResponse); // Ensure the vehicle was added
//        
//        // Delete the vehicle
//        int id = 6; // Assuming the vehicle ID is 1
//        Response deleteResponse = instance.deleteVehicle(id);
//        assertNotNull(deleteResponse); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus()); // Ensure the status code is 200 (OK)
//    }
//}