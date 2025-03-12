package DriverDetails;

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sachintha
 */
public class DriversResourceTest {

    public DriversResourceTest() {
    }

    @BeforeEach
    public void setUp() {
        // Initialize any resources or setup required before each test
    }

    @AfterEach
    public void tearDown() {
        // Clean up resources after each test
    }

    /**
     * Test of getJson method, of class DriversResource.
     */
    @Test
    public void testGetJson() {
        System.out.println("getJson");
        DriversResource instance = new DriversResource();
        
        // Test the getJson method
        String result = instance.getJson();
        assertNotNull(result); // Ensure the result is not null
        assertFalse(result.isEmpty()); // Ensure the result is not empty
    }

    /**
     * Test of addDriver method, of class DriversResource.
     */
    @Test
    public void testAddDriver() {
        System.out.println("addDriver");
        DriversResource instance = new DriversResource();
        
         //Create a new driver JSON string
        String content = "{\"name\": \"John Doe\", \"phoneNumber\": \"1234567890\", \"licenseNumber\": \"D1234567\", \"availability\": \"Available\", \"vehicleID\": 1}";

        
        // Test the addDriver method
        Response result = instance.addDriver(content);
        assertNotNull(result); // Ensure the response is not null
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus()); // Ensure the status code is 201 (Created)
    }

    /**
     * Test of getDriverById method, of class DriversResource.
     */
    @Test
    public void testGetDriverById() {
        System.out.println("getDriverById");
        DriversResource instance = new DriversResource();
        
//        // Add a driver first
//        String addContent = "{\"name\": \"John Doe\", \"phoneNumber\": \"1234567890\", \"licenseNumber\": \"D1234567\", \"availability\": \"Available\", \"vehicleID\": 4}";
//        Response addResponse = instance.addDriver(addContent);
//        assertNotNull(addResponse); // Ensure the driver was added
        
        // Test the getDriverById method
        int id = 10; // Assuming the driver ID is 1
        Response result = instance.getDriverById(id);
        assertNotNull(result); // Ensure the response is not null
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
    }

    /**
     * Test of getAllDrivers method, of class DriversResource.
     */
    @Test
    public void testGetAllDrivers() {
        System.out.println("getAllDrivers");
        DriversResource instance = new DriversResource();
        
        // Test the getAllDrivers method
        Response result = instance.getAllDrivers();
        assertNotNull(result); // Ensure the response is not null
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
    }

    /**
     * Test of updateDriver method, of class DriversResource.
     */
    @Test
    public void testUpdateDriver() {
        System.out.println("updateDriver");
        DriversResource instance = new DriversResource();
        
//        // Add a driver first
//        String addContent = "{\"name\": \"John Doe\", \"phoneNumber\": \"1234567890\", \"licenseNumber\": \"D1234567\", \"availability\": \"Available\", \"vehicleID\": }";
//        Response addResponse = instance.addDriver(addContent);
//        assertNotNull(addResponse); // Ensure the driver was added
        
        // Update the driver
        int id = 10; // Assuming the driver ID is 1
        String updateContent = "{\"name\": \"Jane Doe\", \"phoneNumber\": \"0987654321\", \"licenseNumber\": \"D7654321\", \"availability\": \"On Trip\", \"vehicleID\": 1}";
        Response updateResponse = instance.updateDriver(id, updateContent);
        assertNotNull(updateResponse); // Ensure the response is not null
        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus()); // Ensure the status code is 200 (OK)
    }

    /**
     * Test of deleteDriver method, of class DriversResource.
     */
    @Test
    public void testDeleteDriver() {
        System.out.println("deleteDriver");
        DriversResource instance = new DriversResource();
        
//        // Add a driver first
//        String addContent = "{\"name\": \"John Doe\", \"phoneNumber\": \"1234567890\", \"licenseNumber\": \"D1234567\", \"availability\": \"Available\", \"vehicleID\": 1}";
//        Response addResponse = instance.addDriver(addContent);
//        assertNotNull(addResponse); // Ensure the driver was added
//        
        // Delete the driver
        int id = 10; // Assuming the driver ID is 1
        Response deleteResponse = instance.deleteDriver(id);
        assertNotNull(deleteResponse); // Ensure the response is not null
        assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus()); // Ensure the status code is 200 (OK)
    }

   
   
}