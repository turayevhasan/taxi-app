package uz.pdp.back.controller.contracts;

import uz.pdp.back.payload.UserDTO;

import java.util.UUID;

public interface UserController {
    UserDTO findById(UUID id);
    void changePasswordByTelegramBot();
}
