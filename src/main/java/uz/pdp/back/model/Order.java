package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Order implements Serializable {
    private final UUID id = UUID.randomUUID();
    private Trip from;
    private Trip to;
    private Car car;
    private User client;
    private final Long startTime = System.currentTimeMillis();
    private Long endTime;
    private Long amount;
    private boolean active = true;

    public Order(Trip from, Trip to, Car car, User client, Long amount) {
        this.from = from;
        this.to = to;
        this.car = car;
        this.client = client;
        this.amount = amount;
    }
}
