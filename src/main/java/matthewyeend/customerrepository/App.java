/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package matthewyeend.customerrepository;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author matthewyeend
 */
public class App {
    public static void main(String[] args) {
        try (HikariDataSource dataSource = DatabaseConfig.createDataSource()) {
            CustomerRepository repository = new CustomerRepository(dataSource);

            Customer saved = repository.save(new Customer(null, "Ada Lovelace", "ada@example.com"));
            System.out.println("Saved customer with ID: " + saved.id());

            repository.findByEmail("ada@example.com")
                    .ifPresentOrElse(
                            c -> System.out.println("Found customer ID: " + c.id()),
                            () -> System.out.println("No customer found"));
        } finally {
            // Stops the MySQL driver's background thread so Maven exec:java exits promptly.
            AbandonedConnectionCleanupThread.uncheckedShutdown();
        }
    }
}
