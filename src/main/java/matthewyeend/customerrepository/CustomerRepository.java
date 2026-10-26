/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package matthewyeend.customerrepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Optional;
import javax.sql.DataSource;

/**
 *
 * @author matthewyeend
 */
public class CustomerRepository {

    private static final String INSERT_SQL =
            "INSERT INTO customer (full_name, email) VALUES (?, ?)";
    private static final String FIND_BY_EMAIL_SQL =
            "SELECT id, full_name, email FROM customer WHERE email = ?";

    private final DataSource dataSource;

    public CustomerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Customer save(Customer customer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.fullName());
            statement.setString(2, customer.email().strip().toLowerCase());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Customer(keys.getLong(1), customer.fullName(), customer.email().strip().toLowerCase());
                }
                throw new DataAccessException("Customer saved but no ID was returned", null);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DataAccessException("A customer with that email already exists", e);
        } catch (SQLException e) {
            throw new DataAccessException("Unable to save customer", e);
        }
    }

    public Optional<Customer> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {

            statement.setString(1, email.strip().toLowerCase());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(
                            rs.getLong("id"), rs.getString("full_name"), rs.getString("email")));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to look up customer", e);
        }
    }
}
