package uz.pdp.front;

import uz.pdp.back.controller.*;
import uz.pdp.back.controller.contracts.*;
import uz.pdp.back.enums.CarType;
import uz.pdp.back.model.Location;
import uz.pdp.back.model.Trip;
import uz.pdp.back.model.Zone;
import uz.pdp.back.payload.*;
import uz.pdp.back.repository.LocationRepositoryImpl;
import uz.pdp.back.repository.contracts.LocationRepository;
import uz.pdp.back.support.SendNotificationToEmail;
import uz.pdp.back.utils.ConstVariables;
import uz.pdp.back.utils.Input;

import java.util.*;

public class UserFront {
    private static final CardController cardController = CardControllerImpl.getInstance();
    private static final LocationRepository locationRepository = LocationRepositoryImpl.getInstance();

    private static final CarController carController = CarControllerImpl.getInstance();
    private static final OrderController orderController = OrderControllerImpl.getInstance();
    private static final UserController userController = UserControllerImpl.getInstance();
    private static final SendNotificationToEmail sendNotificationToEmail = new SendNotificationToEmail();
    private static final ApplicationController applicationController = ApplicationControllerImpl.getInstance();

    public static void menu(UserDTO currentUser) {
        int choice;
        while (true) {
            userMenu();
            choice = Input.inputInt("Choose >>> ");
            switch (choice) {
                case 1 -> addNewCard(currentUser.id());
                case 2 -> myCards(currentUser.id());
                case 3 -> startTravel(currentUser);
                case 4 -> startTrip(currentUser.id());
                case 5 -> changePassword();
                case 6 -> sendApplicationToBeDriver(currentUser.id());
                case 7 -> {
                    return;
                }

            }
        }
    }

    private static void sendApplicationToBeDriver(UUID userId) {
        System.out.println("----------- Send Application To Admin To be Driver ------------");
        try {
            String name = Input.inputStr("Enter car name : ");
            String number = Input.inputStr("Enter car number : ");
            CarType carType = choosingCartype();

            UserDTO driver = userController.findById(userId);

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
        System.out.println("------------ Car Types-----------");
        System.out.println("""
                1 => Start(default)
                2 => Comfort
                3 => Business
                """);
    }

    private static void changePassword() {
        System.out.println("You can change your password by our Telegram Bot 🤖 " + ConstVariables.BLUE + "@g38_taxi_app_bot" + ConstVariables.RESET);
    }


    private static void startTrip(UUID userId) {
        try {
            List<OrderDTO> myOrders = orderController.getOrdersByUserId(userId);
            OrderDTO lastOrder = myOrders.getLast();

            if (!lastOrder.active()) {
                System.out.println(ConstVariables.CYAN + "You are on the way now ..." + ConstVariables.RESET);
                System.out.println(ConstVariables.CYAN + "You are on the way now ..." + ConstVariables.RESET);
                System.out.println(ConstVariables.CYAN + "You are on the way now ..." + ConstVariables.RESET);
                System.out.println(ConstVariables.CYAN + "You are on the way now ..." + ConstVariables.RESET);

                System.out.println();
                System.out.println("1 => I am come");
                System.out.println("2 => Back to menu");

                int key = Input.inputInt("\nChoose >>> ");

                if (key == 1) {
                    Long userBalance = cardController.reduceBalance(userId, lastOrder.amount());
                    System.out.println(ConstVariables.BLUE + "Your balance now : " + userBalance / 1000 + " ming sum" + ConstVariables.RESET);

                    sendNotificationToEmail.sendNotification(
                            lastOrder.client().email(),
                            "Taxi App", "Your money reduced by - " + lastOrder.amount() / 1000 + " ming sum" +
                                    "<br>Now your balance : " + userBalance / 1000 + " ming sum"
                    );

                    UUID driverId = lastOrder.carDTO().driverDTO().id();
                    Long driverBalance = cardController.increaseBalance(driverId, lastOrder.amount());

                    sendNotificationToEmail.sendNotification(
                            lastOrder.carDTO().driverDTO().email(),
                            "Taxi App", "Your money increased by + " + lastOrder.amount() / 1000 + " ming sum" +
                                    "<br>Now your balance : " + driverBalance / 1000 + " ming sum"
                    );

                }
            } else {
                System.out.println("Driver does not accept your order yet ... ⏳");
            }
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }


    }

    private static void startTravel(UserDTO currentUser) {
        System.out.println("---------------- Start Travel ---------------");
        try {
            System.out.println(ConstVariables.BLUE + "From Location: " + ConstVariables.RESET);
            List<Location> locations = locationRepository.getLocations();
            for (int i = 0; i < locations.size(); i++) {
                System.out.println((i + 1) + " => " + locations.get(i).getName());
            }
            int input1 = Input.inputInt("Choose >>> ") - 1;
            String fromLocationName = locations.get(input1).getName();

            List<Zone> zones = locations.get(input1).getZones();

            for (int i = 0; i < zones.size(); i++) {
                System.out.println((i + 1) + " => " + zones.get(i).getName());
            }
            int input2 = Input.inputInt("Choose >>> ") - 1;

            String fromZone = locations.get(input1).getZones().get(input2).getName();

            Trip from = new Trip(fromLocationName, fromZone);

            System.out.println(ConstVariables.BLUE + "To location: " + ConstVariables.RESET);
            for (int i = 0; i < locations.size(); i++) {
                System.out.println((i + 1) + " => " + locations.get(i).getName());
            }
            int input3 = Input.inputInt("Choose >>> ") - 1;
            String toLocationName = locations.get(input3).getName();

            for (int i = 0; i < zones.size(); i++) {
                System.out.println((i + 1) + " => " + zones.get(i).getName());
            }
            int input4 = Input.inputInt("Choose >>> ") - 1;

            String toZone = locations.get(input3).getZones().get(input4).getName();

            Trip to = new Trip(toLocationName, toZone);

            CarType[] values = CarType.values();
            for (int i = 0; i < values.length; i++) {
                System.out.println((i + 1) + " => " + values[i]);
            }
            int type = Input.inputInt("Choose >>> ") - 1;
            CarType choosenCarType = values[type];

            Long amount;

            if (choosenCarType.equals(CarType.START))
                amount = new Random().nextLong(10000, 20000);
            else if (choosenCarType.equals(CarType.COMFORT))
                amount = new Random().nextLong(20000, 40000);
            else
                amount = new Random().nextLong(40000, 50000);

            System.out.println(ConstVariables.GREEN + "Your trip amount : " + amount / 1000 + " ming sum" + ConstVariables.RESET);

            List<CarDTO> allCars = carController.getAllCars(choosenCarType);

            if (allCars == null || allCars.isEmpty()) {
                System.out.println("\nCars not found ❌");
            } else {
                for (int i = 0; i < allCars.size(); i++) {
                    System.out.println(i + " -> " + allCars.get(i));
                }

                int chooseCar = Input.inputInt("Choose Car: ");

                CarDTO carDTO = allCars.get(chooseCar);

                OrderAddDTO orderAddDTO = new OrderAddDTO(from, to, carDTO, currentUser, amount);

                orderController.save(orderAddDTO);
                System.out.println("Your order request is sent to Driver ✅");
            }

        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

    private static void userMenu() {
        System.out.println("---------------- User Menu -----------------");
        System.out.println("1 => Add new Card");
        System.out.println("2 => My Cards");
        System.out.println("3 => Where are you going ?");
        System.out.println("4 => Check order status");
        System.out.println("5 => Change Password");
        System.out.println("6 => To be Driver");
        System.out.println("7 => Exit");
    }

    private static void myCards(UUID userId) {
        System.out.println("----------------- My Cards -------------------");
        try {
            List<CardDTO> cards = cardController.myCards(userId);
            if (cards == null || cards.isEmpty())
                System.out.println("You have no cards yet ❌");
            else {
                int i = 1;

                for (CardDTO card : cards) {
                    System.out.println(i + " - Card [" + card.cardNumber() + "]" + ", Balance[" + card.balance() / 1000 + " ming sum]");
                    i++;
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

    private static void addNewCard(UUID userId) {
        System.out.println("----------------- Add New Card ---------------------");

        try {
            String cardNumber = Input.inputStr("Card Number : ");
            String cardPassword = Input.inputStr("Card Password : ");
            Long balance = Input.inputLong("Balance :");

            cardController.createCard(new CardDTO(cardNumber, cardPassword, balance, userId, true));
            System.out.println("Card successfully added ✅");

        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }
}
