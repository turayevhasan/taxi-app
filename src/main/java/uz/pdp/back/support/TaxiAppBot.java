package uz.pdp.back.support;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import uz.pdp.back.model.User;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.UserRepository;
import uz.pdp.back.utils.ConstVariables;
import uz.pdp.back.utils.Validators;
import uz.pdp.back.utils.ValidatorsForRegistration;

public class TaxiAppBot {
    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final Validators validators = ValidatorsForRegistration.getInstance();
    private static final TelegramBot bot = new TelegramBot(ConstVariables.BOT_TOKEN);

    private static String foundUserPhone = "";

    public void handle(Update update) {
        Message message = update.message();

        if (message.text() != null && message.text().equals("/start")) {
            SendMessage sendMessage = new SendMessage(message.chat().id(), "Hi " + message.chat().firstName());

            KeyboardButton phoneButton = new KeyboardButton("\uD83D\uDCDE Send phone number");
            phoneButton.requestContact(true);
            ReplyKeyboardMarkup table = new ReplyKeyboardMarkup(phoneButton);
            table.resizeKeyboard(true);
            sendMessage.replyMarkup(table);

            bot.execute(sendMessage);
        } else if (message.contact() != null) {
            String phoneNumber = message.contact().phoneNumber();

            User foundUser = userRepository.findByPhone(phoneNumber);

            if (foundUser == null) {
                SendMessage sendMessage = new SendMessage(message.chat().id(), "You are not registered with this phone number in Taxi app ❌");
                bot.execute(sendMessage);
            } else {
                foundUserPhone = foundUser.getPhone();
                SendMessage sendMessage = new SendMessage(message.chat().id(), "Enter new password :");
                bot.execute(sendMessage);
            }
        } else if (validators.isValidPassword(message.text())) {
            boolean isUpdated = userRepository.updatePassword(message.text(), foundUserPhone);
            if (isUpdated) {
                SendMessage sendMessage = new SendMessage(message.chat().id(), "Successfully updated your password ✅");
                bot.execute(sendMessage);
            } else {
                SendMessage sendMessage = new SendMessage(message.chat().id(), "Something went wrong ❌");
                bot.execute(sendMessage);
            }
        } else if (!validators.isValidPassword(message.text())) {
            SendMessage sendMessage = new SendMessage(message.chat().id(), "Password must be strong ❌");
            bot.execute(sendMessage);
        } else {
            SendMessage sendMessage = new SendMessage(message.chat().id(), "Invalid input ❌");
            bot.execute(sendMessage);
        }
    }
}
