package carsharing;

import carsharing.car.Car;
import carsharing.car.CarDao;
import carsharing.company.Company;
import carsharing.company.CompanyDao;
import carsharing.customer.Customer;
import carsharing.customer.CustomerDao;

import java.util.List;

public class CustomerController {
    private CompanyDao companyDao;
    private CarDao carDao;
    private CustomerDao customerDao;

    public CustomerController(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }

    public void chooseCustomer() {
        List<Customer> customers = customerDao.getCustomers();
        int choice = UserInterface.showCustomerListSelector(customers);
        if (choice != 0) {
            customerMenu(customers.get(choice - 1).getId());
        }
    }

    public void createCustomer() {
        String customerName = UserInterface.showCreateCustomerDialog();
        int elemns = customerDao.addCustomer(customerName);
        if (elemns > 0) {
            UserInterface.printCustomerCreated();
        }
    }


    private void customerMenu(int customerId) {
        boolean back = false;
        while (!back) {
            int choice = UserInterface.showCustomerMainMenu();
            switch (choice) {
                case 1:
                    rentCarWorkflow(customerId);
                    break;
                case 2:
                    returnCar(customerId);
                    break;
                case 3:
                    showRentedCar(customerId);
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    //region RENT A CAR

    private void rentCarWorkflow(int customerId) {
        Customer customer = customerDao.getCustomer(customerId);
        if (customer.getRentedCarId() != 0) {
            UserInterface.printCarRentedError();
        } else {
            Company company = chooseCompanyToRentCar();
            if (company != null) {
                Car car = chooseCarToRent(company);
                if (car != null) {
                    customerDao.rentCar(customerId, car.getId());
                    UserInterface.printRentedCarName(car.getName());
                }
            }
        }
    }

    private Company chooseCompanyToRentCar() {
        List<Company> companyList = companyDao.getCompanies();
        int choice = UserInterface.showCompanyListSelector(companyList);
        if (choice == 0) {
            return null;
        }
        return companyList.get(choice - 1);

    }

    private Car chooseCarToRent(Company company) {
        List<Car> carList = carDao.getAvailableCarsByCompanyId(company.getId());
        int choice =  UserInterface.showCompanyCarListSelector(company.getName(), carList);
        if (choice == 0) {
            return null;
        }
        return carList.get(choice - 1);
    }
    //endregion

    //region RETURN A CAR
    private void returnCar(int customerId) {
        Customer customer = customerDao.getCustomer(customerId);
        if (customer.getRentedCarId() == 0) {
            UserInterface.printNoCarRentedError();
        } else {
            customerDao.cancelCar(customer.getId());
            UserInterface.printReturnedRentedCar();
        }
    }

    //endregion

    //region RENTED CAR
    private void showRentedCar(int customerId) {
        Customer customer = customerDao.getCustomer(customerId);
        int rentedCarId = customer.getRentedCarId();
        if (rentedCarId == 0) {
            UserInterface.printNoCarRentedError();
        } else {
            Car car = carDao.getCar(rentedCarId);
            Company company = companyDao.getCompany(car.getCompanyId());
            UserInterface.printCustomerCar(car.getName(), company.getName());
        }
    }

    //endregion


}
