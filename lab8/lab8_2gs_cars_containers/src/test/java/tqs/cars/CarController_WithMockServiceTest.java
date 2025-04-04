package tqs.cars;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import tqs.cars.boundary.CarController;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarController_WithMockServiceTest {

    @Autowired
    private MockMvc mockForTests;

    @MockBean
    private CarManagerService carManagerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenPostCar_thenCreateCar() throws Exception{
        Car ferrari = new Car("Ferrari", "X5");

        when(carManagerService.save(ferrari)).thenReturn(ferrari);

        mockForTests.perform(post("/api/cars")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(ferrari)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.maker").value("Ferrari"));
    }

    @Test
    public void getAllCars_shouldReturnCars() throws Exception{
        Car car1 = new Car("Ford", "Mustang");
        Car car2 = new Car("Mercedes", "Class B");
        List<Car> cars = Arrays.asList(car1, car2);

        when(carManagerService.getAllCars()).thenReturn(cars);

        mockForTests.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].maker").value("Ford"))
                .andExpect(jsonPath("$[1].model").value("Class B"));
    }
}
