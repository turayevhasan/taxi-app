package uz.pdp.back.controller;

import lombok.Getter;
import uz.pdp.back.controller.contracts.ApplicationController;
import uz.pdp.back.enums.Role;
import uz.pdp.back.model.Application;
import uz.pdp.back.model.Car;
import uz.pdp.back.model.User;
import uz.pdp.back.payload.ApplicationDTO;
import uz.pdp.back.payload.CarDTO;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.repository.ApplicationRepositoryImpl;
import uz.pdp.back.repository.CarRepositoryImpl;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.ApplicationRepository;
import uz.pdp.back.repository.contracts.CarRepository;
import uz.pdp.back.repository.contracts.UserRepository;
import uz.pdp.back.utils.Input;

import java.util.List;

public class ApplicationControllerImpl implements ApplicationController {
    @Getter
    private static final ApplicationController instance = new ApplicationControllerImpl();

    private ApplicationControllerImpl() {
    }

    private static final UserRepository userRepo = UserRepositoryImpl.getInstance();
    private static final ApplicationRepository applicationRepo = ApplicationRepositoryImpl.getInstance();
    private static final CarRepository carRepository = CarRepositoryImpl.getInstance();

    @Override
    public void sendApplication(ApplicationDTO applicationDTO) {
        UserDTO driverDTO = applicationDTO.carDTO().driverDTO();
        User driver = userRepo.findByPhone(driverDTO.phone());

        CarDTO carDTO = applicationDTO.carDTO();
        Car car = new Car(driver, carDTO.name(), carDTO.number(), carDTO.type());

        Application application = new Application(car);
        applicationRepo.save(application);
    }

    @Override
    public void acceptApplication() {
        List<Application> applications = applicationRepo.getAllApplications();

        int countApps = 0;
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).isActive()) {
                System.out.println(i + " -> " + applications.get(i));
                countApps++;
            }
        }
        if (countApps != 0) {
            int i = Input.inputInt("Choose : ");
            applications.get(i).setActive(false);

            User driver = applications.get(i).getCar().getDriver();

            if (driver.getRole().equals(Role.USER)) {
                applications.get(i).getCar().getDriver().setRole(Role.DRIVER);
                userRepo.changeUserRole(driver.getId(), Role.DRIVER);
            }

            applicationRepo.updateApplications(applications);

            Car car = applications.get(i).getCar();

            carRepository.save(car);
        } else {
            System.out.println("You have no active applications now ❌");
        }
    }
}
