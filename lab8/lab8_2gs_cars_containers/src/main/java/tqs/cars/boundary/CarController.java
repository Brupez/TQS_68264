package tqs.cars.boundary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.cars.dto.CarDTO;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarManagerService carManagerService;

    public CarController(CarManagerService injectedCarManagerService) {
        this.carManagerService = injectedCarManagerService;
    }


    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        Car car = new Car(carDTO.getMaker(), carDTO.getModel());
        Car saved = carManagerService.save(car);
        CarDTO response = convertToDto(saved);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<CarDTO> carDTOs = carManagerService.getAllCars()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable(value = "id") Long carId) {
        return carManagerService.getCarDetails(carId)
                .map(car -> ResponseEntity.ok(convertToDto(car)))
                .orElse(ResponseEntity.notFound().build());
    }

    private CarDTO convertToDto(Car car) {
        CarDTO dto = new CarDTO(car.getMaker(), car.getModel());
        dto.setCarId(car.getCarId());
        return dto;
    }
}
