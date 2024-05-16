package uz.pdp.back.repository.contracts;

import uz.pdp.back.model.Car;

import java.util.List;
import java.util.UUID;

public interface CarRepository {
    void save(Car car);

    Car findByDriverId(UUID driverId);

    void delete(Car car);

    List<Car> getCars();
}
