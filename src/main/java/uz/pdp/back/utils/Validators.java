package uz.pdp.back.utils;

public interface Validators {
    boolean isValidPassword(String password);

    boolean isValidEmailAddress(String email);

    boolean isValidPhoneNumber(String phoneNumber);

    boolean isValidCardPassword(String cardPassword);

    boolean isValidCardNumber(String cardNumber);
}
