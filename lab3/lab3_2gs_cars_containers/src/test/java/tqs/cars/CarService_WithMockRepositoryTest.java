package tqs.cars;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import tqs.cars.boundary.CarController;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CarService_WithMockRepositoryTest {

    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    //usefull instances
    private Car A,B,C;

    @BeforeEach
    public void setUp(){
        A = new Car("Ferrari", "X5");
        B = new Car("Honda", "versaoC");
        C = new Car("Mercedes", "Class B");
        List<Car> cars = Arrays.asList(A,B,C);

        Mockito.when(carRepository.findAll()).thenReturn(cars);
        Mockito.when(carRepository.findByCarId(A.getCarId())).thenReturn(Optional.of(A));
        Mockito.when(carRepository.findByCarId(-1L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenSaveCar_theCarShouldBeFound(){
        Car found = carManagerService.save(B);

        assertThat(found.getCarId()).isEqualTo(B.getCarId());
        verify(carRepository,times(1)).findByCarId(B.getCarId());
    }
}
