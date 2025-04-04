package tqs.cars;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
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
// Mock Car Service
class GsCarsContainersApplicationTests {

    // instantiate the container passing selected config
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("demo")
            .withPassword("demopass")
            .withDatabaseName("test_db")
            .withInitScript("classpath:db/migration/V001_INIT.sql");

    @LocalServerPort
    int localPortForTestServer;

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
    public void setUpTestCars() {
        RestAssured.port = localPortForTestServer;
        car1 = repository.save(new Car("kia", "stinger"));
        car2 = repository.save(new Car("tesla", "model x"));
    }

    Car car1, car2;

    @Test
    void whenGetCarById_thenApiReturnsOneCar() {
        given()
                .when()
                .get("/api/cars" + car1.getCarId())
                .then()
                .statusCode(200)
                .body("maker", equalTo("kia"))
                .body("model", equalTo("stinger"));
    }

    @Test
    void whenGetCarById_thenApiReturnsNotFound() {
        given()
                .when()
                .get("/api/cars/999")
                .then()
                .statusCode(404);
    }

    @Test
    void whenGetCarById_thenApiReturnsAllCars() {
        given()
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("maker", equalTo("kia"))
                .body("model", equalTo("stinger"));
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        container.close();
    }

}
