package tqs.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class CarRepository_PersistanceTest {

    @Autowired

    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void whenFindAll_thenReturnAllCars() {

        Car car1 = new Car("Ford", "Mustang");
        Car car2 = new Car("Mercedes", "Class B");
        Car car3 = new Car("Ferrari", "X5");
        entityManager.persistAndFlush(car1);
        entityManager.persistAndFlush(car2);
        entityManager.persistAndFlush(car3);


        List<Car> found = carRepository.findAll();
        assertThat(found).hasSize(3);
        assertThat(found.get(0)).isEqualTo(car1);
        assertThat(found.get(2)).isEqualTo(car3);
    }

    @Test
    public void whenInvalidCar_thenReturedNull(){
        Optional<Car> fromDb = carRepository.findByCarId(-1L);
        assertThat(fromDb).isEmpty();
    }

    @Test
    public void whenFindCarById_thenReturnCar(){
        Car car = new Car("Ferrari", "X5");
        entityManager.persistAndFlush(car);

        Car fromDb = carRepository.findByCarId(car.getCarId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assert fromDb != null;
        assertThat(fromDb.getCarId()).isEqualTo(car.getCarId());
    }

}
