package uz.pdp.front;

import uz.pdp.back.controller.ApplicationControllerImpl;
import uz.pdp.back.controller.OrderControllerImpl;
import uz.pdp.back.controller.UserControllerImpl;
import uz.pdp.back.controller.contracts.ApplicationController;
import uz.pdp.back.controller.contracts.OrderController;
import uz.pdp.back.controller.contracts.UserController;
import uz.pdp.back.enums.CarType;
import uz.pdp.back.payload.ApplicationDTO;
import uz.pdp.back.payload.CarDTO;
import uz.pdp.back.payload.OrderDTO;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.utils.ConstVariables;
import uz.pdp.back.utils.Input;

import java.util.List;
import java.util.UUID;

public class DriverFront {
    private static final ApplicationController applicationController = ApplicationControllerImpl.getInstance();
    private static final OrderController orderController = OrderControllerImpl.getInstance();
    private static final UserController userController = UserControllerImpl.getInstance();

    public static void menu(UserDTO currentUser) {
        int choice;
        while (true) {
            driverMenu();
            choice = Input.inputInt("Choose >>> ");
            switch (choice) {
                case 1 -> sendApplicationToAdmin(currentUser.id());
                case 2 -> myOrders(currentUser.id());
                case 3 -> myBalance(currentUser.id());
                case 4 -> changePassword();
                case 5 -> {
                    return;
                }
            }
        }
    }

    private static void changePassword() {
        System.out.println("You can change your password by our Telegram Bot 🤖 " + ConstVariables.BLUE + "@g38_taxi_app_bot" + ConstVariables.RESET);
    }

    private static void myBalance(UUID driverId) {
        System.out.println("---------------- My Balance --------------");
        try {
            UserDTO driverDTO = userController.findById(driverId);
            double balance = driverDTO.balance();
            System.out.println(ConstVariables.BLUE + "Your balance : " + balance / 1000 + " ming sum" + ConstVariables.RESET);
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

    private static void myOrders(UUID driverId) {
        System.out.println("------------- My Orders ------------");
        try {
            List<OrderDTO> orders = orderController.getOrdersByDriverId(driverId);
            if (!orders.isEmpty()) {
                for (int i = 0; i < orders.size(); i++) {
                    System.out.println(i + " -> " + orders);
                }

                int i = Input.inputInt("Choose order >>> ");
                OrderDTO orderDTO = orders.get(i);

                orderController.acceptOrder(driverId);
            } else {
                System.out.println("You don't have any active orders ❌");
            }
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

    public static void sendApplicationToAdmin(UUID driverId) {
        System.out.println("----------- Send Application To Admin ------------");
        try {
            String name = Input.inputStr("Enter car name : ");
            String number = Input.inputStr("Enter car number : ");
            CarType carType = choosingCartype();

            UserDTO driver = userController.findById(driverId);

            ApplicationDTO applicationDTO = new ApplicationDTO(new CarDTO(name, driver, number, carType));
            applicationController.sendApplication(applicationDTO);
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }

    }

    private static CarType choosingCartype() {
        displayCarTypes();
        switch (Input.inputInt("Choose >>> ")) {
            case 2 -> {
                return CarType.COMFORT;
            }
            case 3 -> {
                return CarType.BUSINESS;
            }
            default -> {
                return CarType.START;
            }
        }
    }

    private static void displayCarTypes() {
        System.out.println("------------ Display Car Types-----------");
        System.out.println("""
                1 => Start(default)
                2 => Comfort
                3 => Business
                """);
    }

    private static void driverMenu() {
        System.out.println("------------ Driver Menu -------------");
        System.out.println("""
                1 => Send Application to Admin
                2 => My Orders
                3 => My Balance
                4 => Change My Password
                5 => Back
                """);
    }

}
