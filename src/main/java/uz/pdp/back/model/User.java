package uz.pdp.back.model;


import lombok.*;
import uz.pdp.back.enums.Role;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private final UUID id = UUID.randomUUID();
    private String name;
    private int age;
    private String phone;
    private String password;
    private String email;
    private Role role;
    private Long balance = 0L;

    public User(String name, int age, String phone, String password, String email, Role role) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}