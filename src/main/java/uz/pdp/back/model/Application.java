package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.back.payload.CarDTO;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Application implements Serializable {
    private final UUID id = UUID.randomUUID();
    private boolean active = true;
    private Car car;

    public Application(Car car) {
        this.car = car;
    }
}
