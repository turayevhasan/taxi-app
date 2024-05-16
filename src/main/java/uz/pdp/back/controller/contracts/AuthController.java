package uz.pdp.back.controller.contracts;

import uz.pdp.back.payload.SignInDTO;
import uz.pdp.back.payload.SignUpDTO;
import uz.pdp.back.payload.UserDTO;

public interface AuthController {
    UserDTO signUp(SignUpDTO dto, String code);

    UserDTO signIn(SignInDTO dto);
}
