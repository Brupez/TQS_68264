package tqs.cars.data;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    public Optional<Car> findByCarId(Long carId);

    public List<Car> findAll();

}
