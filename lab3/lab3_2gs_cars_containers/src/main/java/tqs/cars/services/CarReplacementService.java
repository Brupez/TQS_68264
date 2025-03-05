package tqs.cars.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarReplacementService {

    private final CarRepository carRepository;

    @Autowired
    public CarReplacementService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car findReplacementCar(Car givenCar) {
        List<Car> AllCars = carRepository.findAll();

        List<Car> suitableReplacements = AllCars.stream()
                .filter(car -> !car.equals(givenCar))
                .filter(car -> car.getMaker().equalsIgnoreCase(givenCar.getMaker()) ||
                                    car.getModel().equalsIgnoreCase(givenCar.getModel()))
                .collect(Collectors.toList());

        // return the first suitable replacement or null if none is found
        if(!suitableReplacements.isEmpty()){
            return suitableReplacements.get(0);
        }
        else {
            return null;
        }
    }
}
