package uz.pdp.back.payload;

import java.util.UUID;

public record CardDTO(String cardNumber, String cardPassword, Long balance, UUID userId, boolean isActive) {
}
