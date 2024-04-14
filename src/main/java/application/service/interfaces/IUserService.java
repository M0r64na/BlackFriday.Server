package application.service.interfaces;

import data.model.entity.Role;
import data.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO add dto objects
public interface IUserService {
    void createUser(String username, String password);
    User updateUser(String username, String newPassword);
    Optional<User> getUserById(UUID id);
    List<User> getAllUsers();
    void deleteUserById(UUID id);
    User getUserByUsername(String username);
    void initializeUsers();
}