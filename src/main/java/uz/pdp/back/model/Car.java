package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.back.enums.CarType;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Car implements Serializable {
    private final UUID id = UUID.randomUUID();
    private User driver;
    private String name;
    private String number;
    private CarType type;

}
