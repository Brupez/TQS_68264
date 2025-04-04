package tqs.cars;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.cars.boundary.CarController;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@WebMvcTest(controllers = CarController.class)
public class IntegrationTest {

    @MockBean
    CarManagerService carManagerService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testGetAllCars(){

        Mockito.when(carManagerService.getAllCars()).thenReturn(
                List.of(new Car("Tesla", "Model S"),
                        new Car("Mercedes", "Class B"))
        );

        RestAssuredMockMvc
                .given()
                .when()
                    .get("/api/cars")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("$[0].maker", equalTo("Tesla"))
                    .body("$[1].maker", equalTo("Mercedes"));
    }
}
