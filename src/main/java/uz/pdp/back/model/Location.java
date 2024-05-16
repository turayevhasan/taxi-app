package uz.pdp.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class Location implements Serializable {
    private String name;
    private List<Zone> zones;
}
