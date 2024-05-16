package uz.pdp.back.utils;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorsForRegistration implements Validators {
    @Getter
    private static final Validators instance = new ValidatorsForRegistration();

    private ValidatorsForRegistration() {
    }

    @Override
    public boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(ConstVariables.PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(ConstVariables.PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    @Override
    public boolean isValidCardPassword(String cardPassword) {
        Pattern pattern = Pattern.compile(ConstVariables.CARD_PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(cardPassword);
        return matcher.matches();
    }

    @Override
    public boolean isValidCardNumber(String cardNumber) {
        Pattern pattern = Pattern.compile(ConstVariables.CARD_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    @Override
    public boolean isValidEmailAddress(String emailAddress) {
        Pattern pattern = Pattern.compile(ConstVariables.EMAIL_ADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }
}