package uz.pdp.back.repository;

import lombok.Getter;
import uz.pdp.back.model.Car;
import uz.pdp.back.repository.contracts.CarRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarRepositoryImpl implements CarRepository {
    @Getter
    private static final CarRepository instance = new CarRepositoryImpl();

    private CarRepositoryImpl() {
    }

    private static final String carDataUrl = "database/car_data.txt";
    private static final DataSaverGetter<Car> dataSaverGetter = new DataSaverGetter<>();

    @Override
    public void save(Car car) {
        List<Car> cars = dataSaverGetter.getAllData(carDataUrl);

        cars.add(car);
        dataSaverGetter.writeDataToFile(cars, carDataUrl);
    }


    @Override
    public Car findByDriverId(UUID driverId) {
        List<Car> cars = dataSaverGetter.getAllData(carDataUrl);

        for (Car car : cars) {
            if (car != null && car.getDriver().getId().equals(driverId))
                return car;
        }
        return null;
    }

    @Override
    public void delete(Car car) {
        List<Car> cars = dataSaverGetter.getAllData(carDataUrl);
        cars.remove(car);
        dataSaverGetter.writeDataToFile(cars, carDataUrl);
    }

    @Override
    public List<Car> getCars() {
        return dataSaverGetter.getAllData(carDataUrl);
    }


}
