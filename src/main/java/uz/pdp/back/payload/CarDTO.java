package uz.pdp.back.payload;

import uz.pdp.back.enums.CarType;
import uz.pdp.back.model.User;

import java.io.Serializable;
import java.util.UUID;

public record CarDTO(String name, UserDTO driverDTO, String number, CarType type) implements Serializable {
}
