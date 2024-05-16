package uz.pdp.back.payload;

import uz.pdp.back.model.Car;
import uz.pdp.back.model.Trip;
import uz.pdp.back.model.User;

import java.util.UUID;

public record OrderDTO(UUID id, Trip from, Trip to, CarDTO carDTO, UserDTO client, Long amount, boolean active) {
}
