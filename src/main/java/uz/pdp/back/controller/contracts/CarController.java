package uz.pdp.back.controller.contracts;

import uz.pdp.back.enums.CarType;
import uz.pdp.back.payload.CarDTO;

import java.util.List;
import java.util.UUID;

public interface CarController {
    boolean registerCar(CarDTO carDTO);

    void deleteCar(UUID driverId);

    List<CarDTO> getAllCars(CarType carType);
}
