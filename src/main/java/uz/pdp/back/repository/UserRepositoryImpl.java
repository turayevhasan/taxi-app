package uz.pdp.back.repository;

import lombok.Getter;
import uz.pdp.back.enums.Role;
import uz.pdp.back.model.User;
import uz.pdp.back.repository.contracts.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    @Getter
    private static final UserRepository instance = new UserRepositoryImpl();

    private UserRepositoryImpl() {

    }

    private static final String userDataUrl = "database/user_data.txt";
    private static final DataSaverGetter<User> dataSaverGetter = new DataSaverGetter<>();

    @Override
    public void save(User user) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);

        if (users == null) {
            users = new ArrayList<>();
        }

        users.add(user);
        dataSaverGetter.writeDataToFile(users, userDataUrl);
    }

    @Override
    public void delete(User user) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);
        users.remove(user);
        dataSaverGetter.writeDataToFile(users, userDataUrl);
    }

    @Override
    public boolean updatePassword(String password, String phone) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);
        for (User user : users) {
            if (user.getPhone().equals(phone)) {
                user.setPassword(password);

                dataSaverGetter.writeDataToFile(users, userDataUrl);

                return true;
            }
        }
        return false;
    }


    @Override
    public User findByPhone(String username) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);
        if (users == null) {
            return null;
        }
        for (User user : users) {
            if (user.getPhone().equals(username)) {
                return user;
            }
        }
        return null;
    }


    @Override
    public boolean isUniquePhone(String phone) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);

        if (users == null || users.isEmpty()) {
            return true;
        }

        for (User user : users) {
            if (user.getPhone().equals(phone)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public User getById(UUID id) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);

        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return dataSaverGetter.getAllData(userDataUrl);
    }

    @Override
    public void changeUserRole(UUID id, Role newRole) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setRole(newRole);
                break;
            }
        }
        dataSaverGetter.writeDataToFile(users, userDataUrl);
    }

    @Override
    public void updateUserBalance(User updatedUser) {
        List<User> users = dataSaverGetter.getAllData(userDataUrl);
        for (User user : users) {
            if (user.getId().equals(updatedUser.getId())) {
                user.setBalance(updatedUser.getBalance());
                break;
            }
        }
        dataSaverGetter.writeDataToFile(users, userDataUrl);
    }
}
