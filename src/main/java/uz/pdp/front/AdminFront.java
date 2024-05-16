package uz.pdp.front;

import uz.pdp.back.controller.ApplicationControllerImpl;
import uz.pdp.back.controller.contracts.ApplicationController;
import uz.pdp.back.model.Location;
import uz.pdp.back.model.User;
import uz.pdp.back.model.Zone;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.repository.LocationRepositoryImpl;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.LocationRepository;
import uz.pdp.back.repository.contracts.UserRepository;
import uz.pdp.back.utils.ConstVariables;
import uz.pdp.back.utils.Input;

import java.util.ArrayList;
import java.util.List;


public class AdminFront {
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final ApplicationController applicationController = ApplicationControllerImpl.getInstance();
    private static final LocationRepository locationRepository = LocationRepositoryImpl.getInstance();

    public static void menu() {
        int choice;
        while (true) {
            adminMenu();
            choice = Input.inputInt("Choose >>> ");
            switch (choice) {
                case 1 -> findUserInfo();
                case 2 -> deleteUser();
                case 3 -> applicationController.acceptApplication();
                case 4 -> addLocation();
                case 5 -> addNewAdmin();
                case 6 -> {
                    return;
                }

            }
        }
    }

    private static void addNewAdmin() {
    }

    private static void addLocation() {
        System.out.println("---------------- Add Location -----------------");

        try {
            List<Location> locations = locationRepository.getLocations();

            System.out.println("1 => Add new location");
            System.out.println("2 => Add new zone to existed locations");

            int key = Input.inputInt("Choose >>> ");

            if (key == 1) {
                String newLocationName = Input.inputStr("\nEnter new Location name : ");

                List<Zone> zones = new ArrayList<>();
                String zoneName = Input.inputStr("Please Add new Zone to location : ");

                Zone zone = new Zone(zoneName);
                zones.add(zone);

                Location location = new Location(newLocationName, zones);

                locations.add(location);
                locationRepository.updateLocation(locations);

                System.out.println("Successfully added new zone ✅");
            } else if (key == 2) {
                for (int i = 0; i < locations.size(); i++) {
                    System.out.println(i + " => " + locations.get(i).getName());
                }
                int locationIndex = Input.inputInt("Choose >>> ");

                System.out.println("\nNow, there are these zones ⤵️");
                List<Zone> zones = locations.get(locationIndex).getZones();

                if (zones != null && !zones.isEmpty()) {
                    for (int i = 0; i < zones.size(); i++) {
                        System.out.println(i + " => " + zones.get(i).getName());
                    }
                    String newZone = Input.inputStr("\nEnter new zone name : ");
                    Zone zone = new Zone(newZone);

                    zones.add(zone);

                    locationRepository.updateLocation(locations);

                    System.out.println("Successfully added new zone ✅");
                } else {
                    System.out.println("No zone found ❌");
                }
            } else {
                System.out.println("Invalid choice ❌");
            }

        } catch (Exception e) {
            System.out.println(ConstVariables.RED + e.getMessage() + ConstVariables.RESET);
        }

    }


    private static void deleteUser() {
        System.out.println("--------------- Delete User ----------------");
        try {
            String phone = Input.inputStr("Enter user phone who you want to delete : ");
            User user = userRepository.findByPhone(phone);
            if (user == null) {
                System.out.println("Invalid phone number || User not found ❌");
            } else {
                userRepository.delete(user);
                System.out.println(user.getName() + " - user successfully deleted ✅");
            }

        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

    private static void findUserInfo() {
        try {
            String phone = Input.inputStr("Enter user phone : ");
            User user = userRepository.findByPhone(phone);
            if (user == null) {
                System.out.println("Invalid phone number || User not found ❌");
            } else {
                UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getAge(), user.getPhone(), user.getEmail(), user.getRole(), user.getBalance());
                System.out.println(userDTO);
            }
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

    private static void adminMenu() {
        System.out.println("---------------- Admin Menu ---------------");
        System.out.println("1 => Find User Information");
        System.out.println("2 => Delete User");
        System.out.println("3 => Check Applications");
        System.out.println("4 => Add Location");
        System.out.println("5 => Add New Admin");
        System.out.println("6 => Back");
    }
}
