package application.service;

import application.service.interfaces.IRoleService;
import application.service.interfaces.IUserService;
import common.constant.ExceptionMessage;
import common.exception.NotFoundException;
import data.domain.Role;
import data.domain.enums.RoleName;
import data.domain.User;
import application.util.PasswordEncoder;
import data.repository.interfaces.IUserRepository;
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
    public void createUser(String username, String password) {
        String encodedPassword = PasswordEncoder.encodePassword(password);
        Role clientRole = this.roleService.findRoleByName(RoleName.CLIENT);
        User user = new User(username, encodedPassword);
        user.getRoles().add(clientRole);

        this.userRepository.create(user);
    }

    @Override
    public User updateUser(String username, String newPassword) {
        User user = this.getUserByUsername(username);
        this.updatePassword(user, newPassword);

        return this.userRepository.update(user);
    }

    @Override
    public User getUserById(UUID id) {
        Optional<User> user = this.userRepository.getById(id);
        if(user.isEmpty()) throw new NotFoundException(ExceptionMessage.NO_SUCH_USER_FOUND);

        return user.get();
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
        if(user == null) throw new NotFoundException(ExceptionMessage.NO_SUCH_USER_FOUND);

        return user;
    }

    @Override
    public void initializeUsers() {
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