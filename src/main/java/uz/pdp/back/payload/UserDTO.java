package uz.pdp.back.payload;

import uz.pdp.back.enums.Role;
import uz.pdp.back.model.Location;

import java.util.UUID;

public record UserDTO(UUID id, String name, int age, String phone, String email, Role role, double balance){
}
