package uz.pdp.back.repository;

import lombok.Getter;
import uz.pdp.back.model.Location;
import uz.pdp.back.model.Zone;
import uz.pdp.back.repository.contracts.LocationRepository;

import java.util.ArrayList;
import java.util.List;

public class LocationRepositoryImpl implements LocationRepository {
    @Getter
    private static final LocationRepository instance = new LocationRepositoryImpl();

    private LocationRepositoryImpl() {
    }

    private static final String locationDataUrl = "database/location_data.txt";
    private static final DataSaverGetter<Location> dataSaverGetter = new DataSaverGetter<>();

    /*
    public static void main(String[] args) {
        List<Location> locations = new ArrayList<>();

        ArrayList<Zone> zones1 = new ArrayList<>();
        zones1.add(new Zone("Novza bekati"));
        zones1.add(new Zone("Mirzo Ulug'bek bekati"));
        zones1.add(new Zone("Chilonzor bekati"));
        zones1.add(new Zone("Olmazor bekati"));
        zones1.add(new Zone("Choshtepa bekati"));
        zones1.add(new Zone("O'zgarish bekati"));
        zones1.add(new Zone("Sergeli bekati"));
        Location location1 = new Location("Chilonzor yo'li", zones1);

        ArrayList<Zone> zones2 = new ArrayList<>();
        zones2.add(new Zone("Ming O'rik bekati"));
        zones2.add(new Zone("AmirTemur bekati"));
        zones2.add(new Zone("Yunus Rajabiy bekati"));
        zones2.add(new Zone("Abdulla Qodiriy bekati"));
        zones2.add(new Zone("Minor bekati"));
        zones2.add(new Zone("Bodomzor bekati"));
        zones2.add(new Zone("Shaxriston bekati"));
        zones2.add(new Zone("Yunusobod bekati"));
        zones2.add(new Zone("Turkistob bekati"));
        Location location2 = new Location("Yunusobod yo'li", zones2);

        ArrayList<Zone> zones3 = new ArrayList<>();
        zones3.add(new Zone("Mashinasozlar bekati"));
        zones3.add(new Zone("Toshkent bekati"));
        zones3.add(new Zone("Oybek bekati"));
        zones3.add(new Zone("Kosmonavtlar bekati"));
        zones3.add(new Zone("O'zbekiston bekati"));
        zones3.add(new Zone("Paxtakor bekati"));
        zones3.add(new Zone("G'afur G'ulom bekati"));
        zones3.add(new Zone("Chorsu bekati"));
        zones3.add(new Zone("Tinchlik bekati"));
        zones3.add(new Zone("Beruniy bekati"));
        Location location3 = new Location("O'zbekiston yo'li", zones3);

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        dataSaverGetter.writeDataToFile(locations, locationDataUrl);
    }

     */



    @Override

    public void addLocation(Location location) {
        List<Location> locations = dataSaverGetter.getAllData(locationDataUrl);
        locations.add(location);
        dataSaverGetter.writeDataToFile(locations, locationDataUrl);
    }

    @Override
    public List<Location> getLocations() {
        return dataSaverGetter.getAllData(locationDataUrl);
    }

    @Override
    public void updateLocation(List<Location> locations) {
        dataSaverGetter.writeDataToFile(locations, locationDataUrl);
    }

}
