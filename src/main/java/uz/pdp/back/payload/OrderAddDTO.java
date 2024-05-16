package uz.pdp.back.payload;

import uz.pdp.back.model.Trip;

public record OrderAddDTO(Trip from, Trip to, CarDTO carDTO, UserDTO client, Long amount) {
}
