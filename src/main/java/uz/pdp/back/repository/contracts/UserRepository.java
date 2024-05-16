package uz.pdp.back.repository.contracts;

import uz.pdp.back.enums.Role;
import uz.pdp.back.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    void delete(User user);

    boolean updatePassword(String password, String phone);

    User findByPhone(String phone);

    boolean isUniquePhone(String phone);

    User getById(UUID id);

    List<User> getAllUser();

    void changeUserRole(UUID id, Role newRole);

    void updateUserBalance(User user);
}
