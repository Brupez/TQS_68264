package tqs.cars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;
import tqs.cars.services.CarReplacementService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarReplacementServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarReplacementService carReplacementService;

    private Car A,B,C,D;

    @BeforeEach
    public void setUp(){
        A = new Car("Ferrari", "X5");
        B = new Car("Honda", "C");
        C = new Car("Mercedes", "Class B");
        D = new Car("Mercedes", "Class A"); // same model as C

        A.setCarId(1L);
        B.setCarId(2L);
        C.setCarId(3L);
        D.setCarId(4L);

        List<Car> allCars = Arrays.asList(A,B,C,D);
        when(carRepository.findAll()).thenReturn(allCars);
    }

    @Test
    public void whenReplacementCar_thenCarShouldBeReplaced(){
        Car newCar = carReplacementService.findReplacementCar(C);
        assertThat(newCar).isNotNull();
        assertThat(newCar).isEqualTo(D);
    }

    @Test
    public void whenNoReplacementCar_thenNull(){
        Car newCar = carReplacementService.findReplacementCar(A);
        assertThat(newCar).isNull();
    }
}
