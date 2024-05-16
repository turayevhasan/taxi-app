package uz.pdp.back.controller;

import lombok.Getter;
import uz.pdp.back.controller.contracts.AuthController;
import uz.pdp.back.model.User;
import uz.pdp.back.payload.SignInDTO;
import uz.pdp.back.payload.SignUpDTO;
import uz.pdp.back.payload.UserDTO;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.UserRepository;
import uz.pdp.back.utils.Input;
import uz.pdp.back.utils.Validators;
import uz.pdp.back.utils.ValidatorsForRegistration;

public class AuthControllerImpl implements AuthController {
    @Getter
    private static final AuthController instance = new AuthControllerImpl();

    private AuthControllerImpl() {
    }

    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final Validators validators = ValidatorsForRegistration.getInstance();

    @Override
    public UserDTO signUp(SignUpDTO dto, String code) {
        boolean validPhoneNumber = validators.isValidPhoneNumber(dto.phone());
        if (!validPhoneNumber)
            throw new RuntimeException("This is not valid phone number ❌");

        boolean uniquePhone = userRepository.isUniquePhone(dto.phone());
        if (!uniquePhone)
            throw new RuntimeException("This number is already found ❌");

        boolean validPassword = validators.isValidPassword(dto.password());
        if (!validPassword)
            throw new RuntimeException("Invalid password ❌");

        boolean validEmailAddress = validators.isValidEmailAddress(dto.email());
        if (!validEmailAddress)
            throw new RuntimeException("Invalid email address ❌");

        User user = new User(dto.name(), dto.age(), dto.phone(), dto.password(), dto.email(), dto.role());

        while (true) {
            String userCode = Input.inputStr("Enter verification code : ");
            if (code.equals(userCode))
                break;
            else {
                System.out.println("Incorrect verification code entered ❌");
            }
        }

        userRepository.save(user);

        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getPhone(), user.getEmail(), user.getRole(), user.getBalance());
    }

    @Override
    public UserDTO signIn(SignInDTO dto) {
        User user = userRepository.findByPhone(dto.phone());
        if (user == null)
            throw new RuntimeException("Password or Number is incorrect ❌");
        if (!user.getPassword().equals(dto.password()))
            throw new RuntimeException("Password or Number is incorrect ❌");


        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getPhone(), user.getEmail(), user.getRole(), user.getBalance());
    }

}
