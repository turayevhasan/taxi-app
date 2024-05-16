package uz.pdp.back.utils;

public interface ConstVariables {
    String ADMIN_PHONE = "+998901001010";
    String ADMIN_PASSWORD = "@Admin123";

    String BOT_TOKEN = "6949157495:AAHNBF-KLmdCOpp7GrGIiCuVIfqLktQFQ8Q";
    String BOT_USERNAME = "@g38_taxi_app_bot";


    String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    String PHONE_NUMBER_PATTERN = "^\\+998\\d{9}$";
    String CARD_PASSWORD_PATTERN = "\\d{4}$";
    String CARD_NUMBER_PATTERN = "\\d{16}$";
    String EMAIL_ADDRESS_PATTERN = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";

    String RESET = "\u001B[0m";
    String BLACK = "\u001B[30m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE = "\u001B[34m";
    String PURPLE = "\u001B[35m";
    String CYAN = "\u001B[36m";
    String WHITE = "\u001B[37m";

}
