package uz.pdp.back.controller;

import lombok.Getter;
import uz.pdp.back.controller.contracts.CarController;
import uz.pdp.back.controller.contracts.UserController;
import uz.pdp.back.enums.CarType;
import uz.pdp.back.model.Car;
import uz.pdp.back.model.User;
import uz.pdp.back.payload.CarDTO;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.repository.CarRepositoryImpl;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.CarRepository;
import uz.pdp.back.repository.contracts.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarControllerImpl implements CarController {
    @Getter
    private static final CarController instance = new CarControllerImpl();

    private CarControllerImpl() {
    }

    private static final CarRepository carRepository = CarRepositoryImpl.getInstance();
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final UserController userController = UserControllerImpl.getInstance();

    @Override
    public boolean registerCar(CarDTO carDTO) {
        User driver = userRepository.getById(carDTO.driverDTO().id());
        if (driver == null)
            throw new RuntimeException("Driver not found ❌");

        Car car = new Car(driver, carDTO.name(), carDTO.number(), carDTO.type());
        carRepository.save(car);
        return true;
    }

    @Override
    public void deleteCar(UUID driverId) {
        Car car = carRepository.findByDriverId(driverId);
        carRepository.delete(car);
    }

    @Override
    public List<CarDTO> getAllCars(CarType carType) {
        List<Car> cars = carRepository.getCars();


        List<CarDTO> carDTOS = new ArrayList<>();

        for (Car car : cars) {
            if(car.getType().equals(carType)) {
                UserDTO userDTO = userController.findById(car.getDriver().getId());
                carDTOS.add(new CarDTO(car.getName(), userDTO, car.getNumber(), car.getType()));
            }
        }
        return carDTOS;
    }


}
