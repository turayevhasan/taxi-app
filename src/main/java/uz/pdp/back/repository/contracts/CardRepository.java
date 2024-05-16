package uz.pdp.back.repository.contracts;

import uz.pdp.back.model.Card;

import java.util.List;
import java.util.UUID;

public interface CardRepository {

    void save(Card card);

    boolean isUniqueCard(String cardNumber);

    List<Card> getMyCards(UUID userId);

    void updateUsersBalance(List<Card> cards);
}
