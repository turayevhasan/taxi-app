package uz.pdp.back.repository.contracts;

import uz.pdp.back.model.Location;

import java.util.List;

public interface LocationRepository {
    void addLocation(Location location);
    List<Location> getLocations();
    void updateLocation(List<Location> locations);
}
