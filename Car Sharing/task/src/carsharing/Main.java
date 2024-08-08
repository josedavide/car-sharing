package carsharing;

import carsharing.car.CarDao;
import carsharing.car.CarDaoSQL;
import carsharing.company.CompanyDao;
import carsharing.company.CompanyDaoSQL;
import carsharing.customer.CustomerDao;
import carsharing.customer.CustomerDaoSQL;

public class Main {
    public static void main(String[] args) {
        String databaseFileName = "carsharing";

        // Check if a database file name is provided as a command-line argument
        if (args.length > 0 && args[0].equals("-databaseFileName") && args.length > 1) {
            databaseFileName = args[1];
        }

        // Database URL
        String url = "jdbc:h2:./src/carsharing/db/" + databaseFileName;

        CompanyDao companyDao = new CompanyDaoSQL(url);
        CarDao carDao = new CarDaoSQL(url);
        CustomerDao customerDao = new CustomerDaoSQL(url);

        ManagerController managerController = new ManagerController(companyDao, carDao);
        CustomerController customerController = new CustomerController(companyDao, carDao, customerDao);

        boolean exit = false;
        while (!exit) {
            int choice = UserInterface.showLoginMenu();
            switch (choice) {
                case 1:
                    managerController.managerMenu();
                    break;
                case 2:
                    customerController.chooseCustomer();
                    break;
                    case 3:
                    customerController.createCustomer();
                    break;
                case 0:
                    exit = true;
                    break;
            }
        }
    }
}