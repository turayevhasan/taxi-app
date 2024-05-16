package uz.pdp.back.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataSaverGetter<T> {

    @SuppressWarnings("unchecked")
    public List<T> getAllData(String filePath) {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) reader.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void writeDataToFile(List<T> objects, String filePath) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filePath))) {
            writer.writeObject(objects);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
