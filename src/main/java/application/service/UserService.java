package application.service;

import application.service.interfaces.IRoleService;
import application.service.interfaces.IUserService;
import data.model.entity.Role;
import data.model.entity.enums.RoleName;
import data.model.entity.User;
import application.util.PasswordEncoder;
import data.repository.interfaces.IUserRepository;
import jakarta.ws.rs.NotFoundException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO map dto-s to entities => MAPSTRUCT
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleService roleService;

    public UserService(IUserRepository userRepository, IRoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void createUser(String username, String password) throws RemoteException {
        String encodedPassword = PasswordEncoder.encodePassword(password);
        Role clientRole = this.roleService.findRoleByName(RoleName.CLIENT);
        User user = new User(username, encodedPassword);
        user.getRoles().add(clientRole);

        this.userRepository.create(user);
    }

    @Override
    public void updateUser(String username, String newPassword) {
        User user = this.getUserByUsername(username);
        this.updatePassword(user, newPassword);

        this.userRepository.update(user);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return this.userRepository.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.getAll();
    }

    @Override
    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = this.userRepository.getByUsername(username);
        if(user == null) throw new RuntimeException("No such user exists");

        return user;
    }

    @Override
    public void initializeUsers() throws RemoteException {
        if(!this.userRepository.getAll().isEmpty()) return;

        this.createUser("employee", "employee");

        User employee = this.getUserByUsername("employee");
        employee.getRoles().add(this.roleService.findRoleByName(RoleName.EMPLOYEE));

        this.userRepository.update(employee);
    }

    private void updatePassword(User user, String newPassword) {
        String encodedPassword = user.getPassword();

        if(!PasswordEncoder.verifyPasswords(encodedPassword, newPassword)) {
            encodedPassword = PasswordEncoder.encodePassword(newPassword);
            user.setPassword(encodedPassword);
        }
    }
}