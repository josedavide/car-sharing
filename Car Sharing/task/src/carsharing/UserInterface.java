package carsharing;

import carsharing.car.Car;
import carsharing.company.Company;
import carsharing.customer.Customer;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UserInterface {

    // region MENUS
    public static int showLoginMenu() {
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }

    public static int showManagerMainMenu() {
        System.out.println("1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }

    public static int showCustomerMainMenu() {
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }

    public static void printCompanyName(String companyName) {
        System.out.println("'" + companyName + "' company:");
    }

    public static int showCompanyMenu(String companyName) {
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }
    // endregion

    // region COMPANY DIALOGS
    public static int showCompanyListSelector(List<Company> companyList) {
        if (companyList.isEmpty()) {
            System.out.println("The company list is empty!");
            System.out.println();
            return 0;
        }
        System.out.println("Choose a company:");
        AtomicInteger index = new AtomicInteger(1);
        companyList.forEach(company -> {
            System.out.println(index.getAndIncrement() + ". " + company.getName());
        });

        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }

    public static String showCreateCompanyDialog() {
        System.out.println("Enter the company name:");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    public static void printCompanyCreated() {
        System.out.println("The company was created");
        System.out.println();
    }
    // endregion

    // region CAR DIALOGS
    public static void printCompanyCarList(String companyName, List<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("The car list is empty!");
            System.out.println();
        } else {
            System.out.println("'" + companyName + "' cars:");
            AtomicInteger index = new AtomicInteger(1);
            carList.forEach(car -> {
                System.out.println(index.getAndIncrement() + ". " + car.getName());
            });
            System.out.println();
        }
    }

    public static int showCompanyCarListSelector(String companyName, List<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("No available cars in the '" + companyName + "' company");
            System.out.println();
            return 0;
        }
        System.out.println("Choose a car:");
        AtomicInteger index = new AtomicInteger(1);
        carList.forEach(car -> {
            System.out.println(index.getAndIncrement() + ". " + car.getName());
        });
        System.out.println();

        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }

    public static String showCreateCarDialog() {
        System.out.println("Enter the car name:");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    public static void printCarCreated() {
        System.out.println("The car was added");
        System.out.println();
    }

    // endregion

    // region CUSTOMER DIALOGS

    public static String showCreateCustomerDialog() {
        System.out.println("Enter the customer name:");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        System.out.println();
        return choice;
    }

    public static void printCustomerCreated() {
        System.out.println("The customer was added!");
        System.out.println();
    }

    public static int showCustomerListSelector(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
            return 0;
        }
        System.out.println("Choose a customer:");
        AtomicInteger index = new AtomicInteger(1);
        customerList.forEach(customer -> {
            System.out.println(index.getAndIncrement() + ". " + customer.getName());
        });

        System.out.println("0. Back");

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();
        System.out.println();
        return choice;
    }

    public static void printCustomerCar(String carName, String companyName) {
        System.out.println("Your rented car:");
        System.out.println(carName);
        System.out.println("Company:");
        System.out.println(companyName);
    }

    public static void printNoCarRentedError() {
        System.out.println("You didn't rent a car!");
        System.out.println();
    }

    public static void printCarRentedError() {
        System.out.println("You've already rented a car!");
        System.out.println();
    }

    public static void printRentedCarName(String carName) {
        System.out.println("You rented '" + carName + "'");
        System.out.println();
    }

    public static void printReturnedRentedCar(){
        System.out.println("You've returned a rented car!");
        System.out.println();
    }

    // endregion


}
