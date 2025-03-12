//package com.mycompany.cabxpert_back.end;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author sachintha
// */
//public class UserResourceTest {
//
//    public UserResourceTest() {
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
//     * Test of getUsers method, of class UserResource.
//     */
//    @Test
//    public void testGetUsers() {
//        System.out.println("getUsers");
//        UserResource instance = new UserResource();
//        
//        // Test the getUsers method
//        String result = instance.getUsers();
//        assertNotNull(result); // Ensure the result is not null
//        assertFalse(result.isEmpty()); // Ensure the result is not empty
//    }
//
//    /**
//     * Test of addUser method, of class UserResource.
//     */
//    @Test
//    public void testAddUser() {
//        System.out.println("addUser");
//        UserResource instance = new UserResource();
//        
//        // Create a new user JSON string
//        String jsonData = "{\"username\": \"user\", \"password\": \"testpass\", \"fullName\": \"Test User\", \"email\": \"testuser@example.com\", \"phoneNumber\": \"1234567890\", \"address\": \"123 Test St\", \"nic\": \"123456789V\", \"role\": \"Staff\"}";
//        
//        // Test the addUser method
//        String result = instance.addUser(jsonData);
//        assertNotNull(result); // Ensure the result is not null
//        assertTrue(result.contains("User added successfully")); // Ensure the user was added
//    }
//
//    /**
//     * Test of updateUser method, of class UserResource.
//     */
//    @Test
//    public void testUpdateUser() {
//        System.out.println("updateUser");
//        UserResource instance = new UserResource();
//
//        
////        String jsonData = "{\"username\": \"testuser\", \"password\": \"testpass\", \"fullName\": \"Test User\", \"email\": \"testuser@example.com\", \"phoneNumber\": \"1234567890\", \"address\": \"123 Test St\", \"nic\": \"123456789V\", \"role\": \"Staff\"}";
////        String addResult = instance.addUser(jsonData);
////        assertNotNull(addResult); // Ensure the user was added
//
//        // Update the user
//        int userId = 15; // Assuming the user ID is 1
//        String updatedJsonData = "{\"username\": \"updateduser\", \"password\": \"updatedpass\", \"fullName\": \"Updated User\", \"email\": \"updateduser@example.com\", \"phoneNumber\": \"0987654321\", \"address\": \"456 Updated St\", \"nic\": \"987654321V\", \"role\": \"Admin\"}";
//        String updateResult = instance.updateUser(userId, updatedJsonData);
//        assertNotNull(updateResult); // Ensure the result is not null
//        assertTrue(updateResult.contains("User updated successfully")); // Ensure the user was updated
//    }
//
//    /**
//     * Test of deleteUser method, of class UserResource.
//     */
//    @Test
//    public void testDeleteUser() {
//        System.out.println("deleteUser");
//        UserResource instance = new UserResource();
//
//        //Add a user first
//        String jsonData = "{\"username\": \"testuser\", \"password\": \"testpass\", \"fullName\": \"Test User\", \"email\": \"testuser@example.com\", \"phoneNumber\": \"1234567890\", \"address\": \"123 Test St\", \"nic\": \"123456789V\", \"role\": \"Staff\"}";
//        String addResult = instance.addUser(jsonData);
//        assertNotNull(addResult); // Ensure the user was added
//
//         //Delete the user
//        int userId = 15; // Assuming the user ID is 1
//        String deleteResult = instance.deleteUser(userId);
//        assertNotNull(deleteResult); // Ensure the result is not null
//        assertTrue(deleteResult.contains("User deleted successfully")); // Ensure the user was deleted
//    }
//}