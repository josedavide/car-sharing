package carsharing.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoSQL implements CompanyDao {
    private Connection connection;

    public CompanyDaoSQL(String url) {
        try {
            this.connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true); // Enable auto-commit mode

            // Create the COMPANY table
            String sql = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "NAME VARCHAR(255) UNIQUE NOT NULL" +
                    ")";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);

            } catch (SQLException e) {
            System.out.println("Error creating COMPANY table: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public Company getCompany(int id) {
        String select = "SELECT * FROM COMPANY WHERE id = ?";
        Company company = null;

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(select)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = new Company(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error getting company: " + e.getMessage());
        }
        return company;
    }

    @Override
    public List<Company> getCompanies() {
        String select = "SELECT * FROM COMPANY";
        List<Company> companies = new ArrayList<>();

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(select)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Company company = new Company(resultSet.getInt("id"), resultSet.getString("name"));
                companies.add(company);
            }
        } catch (SQLException e) {
            System.out.println("Error getting companies: " + e.getMessage());
        }
        return companies;
    }

    @Override
    public int addCompany(String companyName) {
        String insert = "INSERT INTO COMPANY (name) VALUES (?)";
        int elemns = 0;

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insert)) {
            preparedStatement.setString(1, companyName);
            elemns = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding company: " + e.getMessage());
        }
        return elemns;
    }
}
