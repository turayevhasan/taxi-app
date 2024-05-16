package uz.pdp.back.controller.contracts;

import uz.pdp.back.payload.CardDTO;

import java.util.List;
import java.util.UUID;

public interface CardController {
    void createCard(CardDTO dto);

    List<CardDTO> myCards(UUID userId);

    Long reduceBalance(UUID userId, Long balance);

    Long increaseBalance(UUID driverId, Long balance);

}
