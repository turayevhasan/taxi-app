package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Card implements Serializable {
    private final UUID id = UUID.randomUUID();
    private String cardNumber;
    private String cardPassword;
    private Long balance;
    private UUID userId;
    private boolean isActive;
}
