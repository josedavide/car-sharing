package carsharing.car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoSQL implements CarDao {

    private Connection connection;
    public CarDaoSQL(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
            this.connection.setAutoCommit(true);

            // Create the CAR table
            String sql = "CREATE TABLE IF NOT EXISTS CAR (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) UNIQUE NOT NULL," +
                    "COMPANY_ID INT NOT NULL," +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)" +
                    ")";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);

            } catch (SQLException e) {
                System.out.println("Error creating CAR table: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public Car getCar(int id) {
        String select = "SELECT * FROM CAR WHERE id = ?";
        Car car = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(select)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                car = new Car(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("company_id"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting car: " + e.getMessage());
        }
        return car;
    }


    @Override
    public int addCar(int companyId, String carName) {
        String insert = "INSERT INTO CAR (name, company_id) VALUES (?, ?)";
        int elemns = 0;

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insert)) {
            preparedStatement.setString(1, carName);
            preparedStatement.setInt(2, companyId);
            elemns = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding car: " + e.getMessage());
        }
        return elemns;
    }

    @Override
    public List<Car> getCarsByCompanyId(int companyId) {
        String select = "SELECT * FROM CAR WHERE company_id = ?";
        List<Car> cars = new ArrayList<>();

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(select)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("company_id"));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("Error getting cars: " + e.getMessage());
        }
        return cars;
    }

    @Override
    public List<Car> getAvailableCarsByCompanyId(int companyId) {
        String query = "SELECT c.ID, c.NAME, c.COMPANY_ID " +
                "FROM CAR c " +
                "WHERE c.COMPANY_ID = ? AND c.ID NOT IN (" +
                "    SELECT RENTED_CAR_ID " +
                "    FROM CUSTOMER " +
                "    WHERE RENTED_CAR_ID IS NOT NULL" +
                ")";

        List<Car> cars = new ArrayList<>();

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("company_id"));
                cars.add(car);
            }
        } catch (SQLException e) {
            System.out.println("Error getting cars: " + e.getMessage());
        }
        return cars;

    }
}
