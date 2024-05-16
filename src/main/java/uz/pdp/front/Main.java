package uz.pdp.front;

import uz.pdp.back.controller.AuthControllerImpl;
import uz.pdp.back.controller.UserControllerImpl;
import uz.pdp.back.controller.contracts.AuthController;
import uz.pdp.back.controller.contracts.UserController;
import uz.pdp.back.enums.Role;
import uz.pdp.back.payload.SignInDTO;
import uz.pdp.back.payload.SignUpDTO;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.support.SendNotificationToEmail;
import uz.pdp.back.utils.ConstVariables;
import uz.pdp.back.utils.Input;

import java.util.Random;

public class Main {
    private final static AuthController authController = AuthControllerImpl.getInstance();
    private final static SendNotificationToEmail sendNotificationToEmail = new SendNotificationToEmail();
    private final static UserController userController = UserControllerImpl.getInstance();
    private static boolean adminEntered = false;

    public static void main(String[] args) {
        userController.changePasswordByTelegramBot();
        while (true) {
            switch (menu()) {

                case 1 -> {
                    String phone = Input.inputStr("Phone : ");
                    String password = Input.inputStr("Password : ");

                    if (phone.equals(ConstVariables.ADMIN_PHONE) && password.equals(ConstVariables.ADMIN_PASSWORD)) {
                        adminEntered = true;
                        AdminFront.menu();
                    }
                    if (adminEntered) {
                        adminEntered = false;
                        break;
                    }

                    UserDTO currentUser = signIn(phone, password);

                    if (currentUser != null) {
                        switch (currentUser.role()) {
                            case USER -> UserFront.menu(currentUser);
                            case DRIVER -> DriverFront.menu(currentUser);
                        }
                    } else {
                        System.out.println("Cannot signed in ❌");
                        System.out.println("If you forgot your password, You can change your password by our Telegram Bot 🤖 " + ConstVariables.BLUE + "@g38_taxi_app_bot" + ConstVariables.RESET);
                    }
                }
                case 2 -> signUp();
                default -> System.out.println("Invalid operation ❌");
            }
        }


    }


    private static int menu() {
        System.out.println("------------------ Registration ------------------");
        System.out.println("1 => Sign In");
        System.out.println("2 => Sign Up");
        return Input.inputInt("Choose >>> ");
    }


    private static UserDTO signIn(String phone, String password) {
        System.out.println("---------------- Sign In ------------------");
        try {
            UserDTO userDTO = authController.signIn(new SignInDTO(phone, password));
            System.out.println("Successfully logged in ✅");
            return userDTO;

        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
            return null;
        }
    }

    private static void signUp() {
        System.out.println("---------------- Sign Up ------------------");

        try {
            String name = Input.inputStr("Name : ");
            int age = Input.inputInt("Age : ");
            String phone = Input.inputStr("Phone : ");
            String password = Input.inputStr("Password : ");
            String email = Input.inputStr("Email :");

            Role[] roles = Role.values();
            for (int i = 0; i < roles.length - 1; i++) {
                System.out.println(i + " -> " + roles[i]);
            }

            int type = Input.inputInt("Choose >>> ");
            Role role = Role.values()[type];

            String code = String.valueOf(new Random().nextInt(1000, 9999));

            sendNotificationToEmail.sendNotification(
                    email,
                    "Registration For Taxi App",
                    name + " - your confirmation password : " + code

            );

            authController.signUp(new SignUpDTO(name, age, phone, password, email, role), code);

            System.out.println("User successfully registered ✅");

        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage() + ConstVariables.RESET);
        }
    }

}
