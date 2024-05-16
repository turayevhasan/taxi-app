package uz.pdp.back.payload;

import uz.pdp.back.enums.Role;

public record SignUpDTO(String name, int age, String phone, String password, String email, Role role) {
}
