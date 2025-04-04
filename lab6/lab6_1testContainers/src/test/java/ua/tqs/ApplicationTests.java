package ua.tqs;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.tqs.model.Customer;
import ua.tqs.repository.CustomerRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ApplicationTests {


    @Autowired
    private CustomerRepository customerRepository;

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("root")
            .withPassword("root")
            .withDatabaseName("test");


    @DynamicPropertySource
    static void dbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // ensure fresh schema
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.show-sql", () -> "true");
    }

    @Test
    @Order(1)
    void contextLoads() {

        Customer customer = new Customer();
        customer.setName("John Doe");

        customerRepository.save(customer);

        System.out.println("Context loads");
    }

    @Test
    @Order(2)
    void testRetrieveCustomer() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
        assertThat(customers.get(0).getName()).isEqualTo("John Doe");

        System.out.println("Retrieved Customers: " + customers);
    }

    @Test
    @Order(3)
    void testUpdateCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        customer.setName("Jane Doe");

        customerRepository.save(customer);
        Customer updatedCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertThat(updatedCustomer).isNotNull();
        assert updatedCustomer != null;
        assertThat(updatedCustomer.getName()).isEqualTo("Jane Doe");

        System.out.println("Updated Customer: " + updatedCustomer);
    }

    @Test
    @Order(4)
    void testDeleteCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        customerRepository.delete(customer);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();

        System.out.println("Deleted Customer: " + customer);
    }


}
