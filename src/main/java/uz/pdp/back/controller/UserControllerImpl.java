package uz.pdp.back.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.Getter;
import uz.pdp.back.controller.contracts.UserController;
import uz.pdp.back.model.User;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.UserRepository;
import uz.pdp.back.support.TaxiAppBot;
import uz.pdp.back.utils.ConstVariables;

import java.util.UUID;

public class UserControllerImpl implements UserController {
    @Getter
    private static final UserController instance = new UserControllerImpl();

    private UserControllerImpl() {
    }

    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();

    @Override
    public UserDTO findById(UUID id) {
        User user = userRepository.getById(id);
        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getPhone(), user.getEmail(), user.getRole(), user.getBalance());
    }

    @Override
    public void changePasswordByTelegramBot() {
        try {
            TelegramBot telegramBot = new TelegramBot(ConstVariables.BOT_TOKEN);

            TaxiAppBot taxiAppBot = new TaxiAppBot();
            telegramBot.setUpdatesListener(updates -> {
                for (Update update : updates) {
                    taxiAppBot.handle(update);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }, Throwable::printStackTrace);
        } catch (Exception e) {
            System.out.println(ConstVariables.RED + "User has an error : " + e.getMessage());
        }

    }

}
