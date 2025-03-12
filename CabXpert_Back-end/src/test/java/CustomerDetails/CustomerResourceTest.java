//package CustomerDetails;
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
//public class CustomerResourceTest {
//
//    public CustomerResourceTest() {
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
//     * Test of getJson method, of class CustomerResource.
//     */
//    @Test
//    public void testGetJson() {
//        System.out.println("getJson");
//        CustomerResource instance = new CustomerResource();
//        
//        // Test the getJson method
//        Response result = instance.getJson();
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of addCustomer method, of class CustomerResource.
//     */
//    @Test
//    public void testAddCustomer() {
//        System.out.println("addCustomer");
//        CustomerResource instance = new CustomerResource();
//        
//        // Create a new customer JSON string
//        String content = "{\"name\": \"John Doe\", \"address\": \"123 Main St\", \"nic\": \"123456789V\", \"phoneNumber\": \"1234567890\", \"registrationDate\": \"2025-10-01\"}";
//        
//        // Test the addCustomer method
//        Response result = instance.addCustomer(content);
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus()); // Ensure the status code is 201 (Created)
//    }
//
//    /**
//     * Test of getAllCustomers method, of class CustomerResource.
//     */
//    @Test
//    public void testGetAllCustomers() {
//        System.out.println("getAllCustomers");
//        CustomerResource instance = new CustomerResource();
//        
//        // Test the getAllCustomers method
//        Response result = instance.getAllCustomers();
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of updateCustomer method, of class CustomerResource.
//     */
//    @Test
//    public void testUpdateCustomer() {
//        System.out.println("updateCustomer");
//        CustomerResource instance = new CustomerResource();
//        
////        // Add a customer first
////        String addContent = "{\"name\": \"John Doe\", \"address\": \"123 Main St\", \"nic\": \"123456789V\", \"phoneNumber\": \"1234567890\", \"registrationDate\": \"2025-10-01\"}";
////        Response addResponse = instance.addCustomer(addContent);
////        assertNotNull(addResponse); // Ensure the customer was added
//        
//        // Update the customer
//        int id = 5; // Assuming the customer ID is 1
//        String updateContent = "{\"name\": \"Jane Doe\", \"address\": \"456 Elm St\", \"nic\": \"987654321V\", \"phoneNumber\": \"0987654321\", \"registrationDate\": \"2026-10-02\"}";
//        Response updateResponse = instance.updateCustomer(id, updateContent);
//        assertNotNull(updateResponse); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of deleteCustomer method, of class CustomerResource.
//     */
//    @Test
//    public void testDeleteCustomer() {
//        System.out.println("deleteCustomer");
//        CustomerResource instance = new CustomerResource();
//        
//        // Add a customer first
//        String addContent = "{\"name\": \"John Doe\", \"address\": \"123 Main St\", \"nic\": \"123456789V\", \"phoneNumber\": \"1234567890\", \"registrationDate\": \"2026-10-01\"}";
//        Response addResponse = instance.addCustomer(addContent);
//        assertNotNull(addResponse); // Ensure the customer was added
//        
//         //Delete the customer
//        int id = 5; // Assuming the customer ID is 1
//        Response deleteResponse = instance.deleteCustomer(id);
//        assertNotNull(deleteResponse); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), deleteResponse.getStatus()); // Ensure the status code is 200 (OK)
//    }
//}