package uz.pdp.back.repository;

import lombok.Getter;
import uz.pdp.back.model.Card;
import uz.pdp.back.model.User;
import uz.pdp.back.repository.contracts.CardRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardRepositoryImpl implements CardRepository {
    @Getter
    private static final CardRepository instance = new CardRepositoryImpl();

    private CardRepositoryImpl() {
    }

    private static final String cardDataUrl = "database/card_data.txt";
    private static final DataSaverGetter<Card> dataSaverGetter = new DataSaverGetter<>();

    @Override
    public void save(Card card) {
        List<Card> cards = dataSaverGetter.getAllData(cardDataUrl);

        cards.add(card);
        dataSaverGetter.writeDataToFile(cards, cardDataUrl);
    }

    @Override
    public boolean isUniqueCard(String cardNumber) {
        List<Card> cards = dataSaverGetter.getAllData(cardDataUrl);
        if (cards == null) {
            return true;
        }
        for (Card card : cards) {
            if (card != null && card.getCardNumber().equals(cardNumber))
                return false;
        }
        return true;
    }

    @Override
    public List<Card> getMyCards(UUID userId) {
        List<Card> cards = dataSaverGetter.getAllData(cardDataUrl);
        List<Card> myCards = new ArrayList<>();

        for (Card card : cards) {
            if (card != null && card.getUserId().equals(userId))
                myCards.add(card);
        }
        return myCards;
    }

    @Override
    public void updateUsersBalance(List<Card> cards) {
        dataSaverGetter.writeDataToFile(cards, cardDataUrl);
    }


}
