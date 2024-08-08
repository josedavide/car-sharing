package carsharing;

import carsharing.car.Car;
import carsharing.car.CarDao;
import carsharing.company.Company;
import carsharing.company.CompanyDao;

import java.util.List;

public class ManagerController {
    private CompanyDao companyDao;
    private CarDao carDao;

    public ManagerController(CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }

    public void managerMenu() {
        boolean back = false;
        while (!back) {
            int choice = UserInterface.showManagerMainMenu();
            switch (choice) {
                case 1:
                    listCompanies();
                    break;
                case 2:
                    createCompany();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void listCompanies() {
        List<Company> companyList = companyDao.getCompanies();
        int choice = UserInterface.showCompanyListSelector(companyList);
        if (choice != 0) {
            companyMenu(companyList.get(choice - 1));
        }
    }

    private void companyMenu(Company company) {
        boolean back = false;
        UserInterface.printCompanyName(company.getName());
        while (!back) {
            int choice = UserInterface.showCompanyMenu(company.getName());
            switch (choice) {
                case 1:
                    listCompanyCars(company);
                    break;
                case 2:
                    createCar(company.getId());
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void listCompanyCars(Company company) {
        List<Car> carList = carDao.getCarsByCompanyId(company.getId());
        UserInterface.printCompanyCarList(company.getName(), carList);
    }

    private void createCompany() {
        String companyName = UserInterface.showCreateCompanyDialog();
        int elemns = companyDao.addCompany(companyName);
        if (elemns > 0) {
            UserInterface.printCompanyCreated();
        }
    }

    private void createCar(int companyId) {
        String carName = UserInterface.showCreateCarDialog();
        int elemns = carDao.addCar(companyId, carName);
        if (elemns > 0) {
            UserInterface.printCarCreated();
        }
    }
}
