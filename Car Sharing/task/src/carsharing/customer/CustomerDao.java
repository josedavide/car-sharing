package carsharing.customer;

import java.util.List;

public interface CustomerDao {
    Customer getCustomer(int id);
    List<Customer> getCustomers();
    int addCustomer(String name);
    void rentCar(int customerId, int carId);
    void cancelCar(int customerId);
}
