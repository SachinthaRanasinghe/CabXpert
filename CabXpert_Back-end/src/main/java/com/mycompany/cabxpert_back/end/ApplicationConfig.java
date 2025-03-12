/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cabxpert_back.end;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author sachintha
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Bills.BillsResource.class);
        resources.add(Bookings.AddBookingResource.class);
        resources.add(Bookings.ViewBookingsResource.class);
        resources.add(CustomerDetails.CustomerResource.class);
        resources.add(DriverDetails.DriversResource.class);
        resources.add(Vehicle.VehicleResource.class);
        resources.add(com.mycompany.cabxpert_back.end.LoginResource.class);
        resources.add(com.mycompany.cabxpert_back.end.RegisterResource.class);
        resources.add(com.mycompany.cabxpert_back.end.UserResource.class);
    }
    
}
