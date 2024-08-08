package carsharing.customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoSQL implements CustomerDao {

    private Connection connection;

    public CustomerDaoSQL(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true); // Enable auto-commit mode

            // Create the CUSTOMER table
            String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) UNIQUE NOT NULL," +
                    "RENTED_CAR_ID INT," +
                    "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID)" +
                    ")";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);

            } catch (SQLException e) {
                System.out.println("Error creating CUSTOMER table: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public Customer getCustomer(int id) {
        String select = "SELECT * FROM CUSTOMER WHERE id = ?";
        Customer customer = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(select)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("rented_car_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting customer: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CUSTOMER";
            try (Statement stmt = connection.createStatement()) {
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        customers.add(new Customer(rs.getInt("ID"),
                                rs.getString("NAME"),
                                rs.getInt("RENTED_CAR_ID")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public int addCustomer(String name) {
        int elemns = 0;
        try {
            String sql = "INSERT INTO CUSTOMER (NAME) VALUES (?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, name);
                elemns = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return elemns;
    }

    @Override
    public void rentCar(int customerId, int carId) {
        try {
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, carId);
                stmt.setInt(2, customerId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelCar(int customerId) {
        try {
            String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, customerId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
