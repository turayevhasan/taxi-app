package uz.pdp.back.controller;

import lombok.Getter;
import uz.pdp.back.controller.contracts.CardController;
import uz.pdp.back.model.Card;
import uz.pdp.back.model.User;
import uz.pdp.back.payload.CardDTO;
import uz.pdp.back.repository.CardRepositoryImpl;
import uz.pdp.back.repository.UserRepositoryImpl;
import uz.pdp.back.repository.contracts.CardRepository;
import uz.pdp.back.repository.contracts.UserRepository;
import uz.pdp.back.utils.Validators;
import uz.pdp.back.utils.ValidatorsForRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardControllerImpl implements CardController {
    @Getter
    private static final CardController instance = new CardControllerImpl();

    private CardControllerImpl() {

    }

    private static final UserRepository userRepository = UserRepositoryImpl.getInstance();
    private static final CardRepository cardRepository = CardRepositoryImpl.getInstance();
    private static final Validators validators = ValidatorsForRegistration.getInstance();

    @Override
    public void createCard(CardDTO dto) {
        boolean isValidCardNumber = validators.isValidCardNumber(dto.cardNumber());
        if (!isValidCardNumber) {
            throw new RuntimeException("Card numbers must includes only 16 digits ❌");
        }

        boolean isValidCardPassword = validators.isValidCardPassword(dto.cardPassword());
        if (!isValidCardPassword) {
            throw new RuntimeException("Card password length must includes 4 digits ❌");
        }

        boolean isUniqueCard = cardRepository.isUniqueCard(dto.cardNumber());
        if (!isUniqueCard) {
            throw new RuntimeException("This card number is not unique ❌");
        }

        Card card = new Card(dto.cardNumber(), dto.cardPassword(), dto.balance(), dto.userId(), true);
        cardRepository.save(card);
    }


    @Override
    public List<CardDTO> myCards(UUID userId) {
        List<CardDTO> all = new ArrayList<>();

        User user = userRepository.getById(userId);
        if (user == null) {
            throw new RuntimeException("This user is not registered ❌");
        }

        List<Card> myCards = cardRepository.getMyCards(userId);
        if (myCards == null) {
            throw new RuntimeException("You have no cards ❌");
        }

        for (Card myCard : myCards) {
            all.add(new CardDTO(myCard.getCardNumber(), myCard.getCardPassword(), myCard.getBalance(), myCard.getUserId(), myCard.isActive()));
        }
        return all;
    }

    @Override
    public Long reduceBalance(UUID userId, Long balance) {
        List<Card> myCards = cardRepository.getMyCards(userId);
        Long updatedBalance;

        if (myCards == null || myCards.isEmpty()) {
            User user = userRepository.getById(userId);

            user.setBalance(user.getBalance() - balance);
            updatedBalance = user.getBalance();

            System.out.println("Trip amount is received from your Taxi balance ✅");

            userRepository.updateUserBalance(user);
        } else {
            Card card = myCards.getFirst();

            card.setBalance(card.getBalance() - balance);
            updatedBalance = card.getBalance();

            cardRepository.updateUsersBalance(myCards);
            System.out.println("Trip amount is received from your Card 💳");
        }

        return updatedBalance;
    }

    @Override
    public Long increaseBalance(UUID driverId, Long balance) {
        User driver = userRepository.getById(driverId);

        driver.setBalance(driver.getBalance() + balance);

        Long updatedBalance = driver.getBalance();
        userRepository.updateUserBalance(driver);

        return updatedBalance;
    }
}
