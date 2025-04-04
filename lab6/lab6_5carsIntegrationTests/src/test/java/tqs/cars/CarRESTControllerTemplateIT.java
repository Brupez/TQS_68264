package tqs.cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// porta aleatoria para diminuir conflitos entre outras portas
// Springboot completa menos o server em si
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
public class CarRESTControllerTemplateIT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    // instancia que o springboot oferece
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    // clean up the database after each test, clean-state
    // but its not always usefull, because we can have tests that depend on the state of the database like finding an employee by name
    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
    void whenValidInput_thenCreateCar() {
        Car car1 = new Car("Honda", "X5");
        restTemplate.postForEntity("/api/cars", car1, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getMaker).contains("Honda");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("honda", "X5");
        createTestCar("Mercedes", "Class B");


        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).contains("honda");

    }

    private void createTestCar(String maker, String model) {
        Car car1 = new Car(maker, model);
        repository.saveAndFlush(car1);
    }
}
