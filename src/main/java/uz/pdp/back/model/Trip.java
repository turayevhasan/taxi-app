package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Trip implements Serializable {
    private String name;
    private String zone;
}
