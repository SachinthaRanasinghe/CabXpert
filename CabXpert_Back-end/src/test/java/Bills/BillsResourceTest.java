package Bills;

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sachintha
 */
public class BillsResourceTest {

    public BillsResourceTest() {
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
     * Test of getJson method, of class BillsResource.
     */
    @Test
    public void testGetJson() {
        System.out.println("getJson");
        BillsResource instance = new BillsResource();
        
        // Test the getJson method
        Response result = instance.getJson();
        assertNotNull(result); // Ensure the response is not null
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
    }

    /**
     * Test of putJson method, of class BillsResource.
     */
//    @Test
//    public void testPutJson() {
//        System.out.println("putJson");
//        BillsResource instance = new BillsResource();
//        
//        // Create a new bill JSON string
//        String content = "{\"bookingID\": 1, \"totalFare\": 100.00, \"tax\": 10.00, \"discount\": 5.00, \"finalAmount\": 105.00, \"paymentStatus\": \"Pending\"}";
//        
//        // Test the putJson method
//        Response result = instance.putJson(content);
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus()); // Ensure the status code is 201 (Created)
//    }

    /**
     * Test of generateBill method, of class BillsResource.
     */
//    @Test
//    public void testGenerateBill() {
//        System.out.println("generateBill");
//        BillsResource instance = new BillsResource();
//        
//        // Test the generateBill method
//        String bookingId = "11"; 
//        Response result = instance.generateBill(bookingId);
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
//    }
//
//    /**
//     * Test of viewAllBills method, of class BillsResource.
//     */
//    @Test
//    public void testViewAllBills() {
//        System.out.println("viewAllBills");
//        BillsResource instance = new BillsResource();
//        
//        // Test the viewAllBills method
//        Response result = instance.viewAllBills();
//        assertNotNull(result); // Ensure the response is not null
//        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus()); // Ensure the status code is 200 (OK)
//    }

    @Test
public void testUpdatePaymentStatus() {
    System.out.println("updatePaymentStatus");
    BillsResource instance = new BillsResource();
    
//    // Add a bill first
//    String billContent = "{\"bookingID\": 1, \"totalFare\": 100.00, \"tax\": 10.00, \"discount\": 5.00, \"finalAmount\": 105.00, \"paymentStatus\": \"Pending\"}";
//    Response addResponse = instance.putJson(billContent);
//    assertNotNull(addResponse); // Ensure the bill was added
//    assertEquals(Response.Status.CREATED.getStatusCode(), addResponse.getStatus()); // Ensure the status code is 201 (Created)

    // Update the payment status
    String billData = "{\"BillID\": 2, \"BookingID\": 1, \"TotalFare\": 1570, \"Tax\": 0, \"Discount\": 500, \"FinalAmount\": 1070, \"PaymentStatus\": \"Paid\"}";

    Response updateResponse = instance.updatePaymentStatus(billData);
    assertNotNull(updateResponse); // Ensure the response is not null
    assertEquals(Response.Status.OK.getStatusCode(), updateResponse.getStatus()); // Ensure the status code is 200 (OK)
}
}