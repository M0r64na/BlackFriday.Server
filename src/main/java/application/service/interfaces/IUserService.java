package application.service.interfaces;

import data.domain.User;
import java.util.List;
import java.util.UUID;

// TODO add dto objects
public interface IUserService {
    void createUser(String username, String password);
    User updateUser(String username, String newPassword);
    User getUserById(UUID id);
    List<User> getAllUsers();
    void deleteUserById(UUID id);
    User getUserByUsername(String username);
    void initializeUsers();
}