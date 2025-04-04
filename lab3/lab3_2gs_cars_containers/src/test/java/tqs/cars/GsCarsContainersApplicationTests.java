package tqs.cars;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create") // note the TestPropertySource to enforce the ddl generation
// Mock Car Service
class GsCarsContainersApplicationTests {

    // instantiate the container passing selected config
    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("demo")
            .withPassword("demopass")
            .withDatabaseName("test_db");
            /// .withInitScript( PATH )
    @LocalServerPort
    int localPortForTestServer;
    Car car1, car2;

    @Autowired
    private CarRepository repository;


    // read configuration from running db
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);

    }

    @BeforeEach
    public void setUpTestCars() throws Exception {
        car1 = repository.save(new Car("kia", "stinger"));
        car2 = repository.save(new Car("tesla", "model x"));
    }

    @Test
    void whenGetCarById_thenApiReturnsOneCar() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars", String.valueOf( car1.getCarId()) )
                .build()
                .toUriString();

        given()
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .body("maker", equalTo("kia"))
                .body("model", equalTo("stinger"));
    }
}
