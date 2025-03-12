package CustomerDetails;

public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String nic;
    private String phoneNumber;
    private String registrationDate; // Added registrationDate field

    public Customer() {}

    public Customer(int customerID, String name, String address, String nic, String phoneNumber, String registrationDate) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegistrationDate() { // Getter for registrationDate
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) { // Setter for registrationDate
        this.registrationDate = registrationDate;
    }
}
