package service;

import application.service.UserService;
import application.service.interfaces.IRoleService;
import application.util.PasswordEncoder;
import common.exception.NotFoundException;
import constant.TestParameter;
import data.domain.Role;
import data.domain.User;
import data.domain.enums.RoleName;
import data.repository.interfaces.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceTest {
    private IUserRepository userRepository;
    private IRoleService roleService;
    private UserService userService;
    private User expectedUser;

    @BeforeEach
    public void setUp() {
        this.userRepository = Mockito.mock(IUserRepository.class);
        this.roleService = Mockito.mock(IRoleService.class);
        this.userService = new UserService(userRepository, roleService);

        this.expectedUser = new User(TestParameter.USERNAME, TestParameter.PASSWORD);
    }

    @Test
    public void testCreateUserShouldCreateUserSuccessfully() {
        Role role = new Role(RoleName.CLIENT);
        Mockito.when(this.roleService.findRoleByName(RoleName.CLIENT))
                .thenReturn(role);
        Mockito.when(this.userRepository.getByUsername(TestParameter.USERNAME))
                .thenReturn(this.expectedUser);


        this.userService.createUser(TestParameter.USERNAME, TestParameter.PASSWORD);

        Mockito.verify(this.userRepository, Mockito.times(1)).create(Mockito.any(User.class));
        User actualUser = this.userRepository.getByUsername(TestParameter.USERNAME);
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(this.expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(this.expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void testUpdateUserShouldUpdateUserSuccessfully() {
        Mockito.when(this.userRepository.getByUsername(TestParameter.USERNAME))
                .thenReturn(this.expectedUser);
        Mockito.when(this.userRepository.update(this.expectedUser))
                .thenReturn(this.expectedUser);

        User actualUser = this.userService.updateUser(TestParameter.USERNAME, TestParameter.NEW_PASSWORD);

        Mockito.verify(this.userRepository, Mockito.times(1)).update(this.expectedUser);
        Assertions.assertTrue(PasswordEncoder.verifyPasswords(actualUser.getPassword(), TestParameter.NEW_PASSWORD));
    }

    @Test
    public void testGetUserByIdShouldThrowNotFoundException()  {
        UUID id = UUID.randomUUID();
        Mockito.when(this.userRepository.getById(id))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                NotFoundException.class,
                () -> this.userService.getUserById(id));
        Mockito.verify(this.userRepository, Mockito.times(1)).getById(id);
    }

    @Test
    public void testGetUserByIdShouldReturnUserSuccessfully() {
        UUID id = UUID.randomUUID();
        this.expectedUser.setId(id);
        Mockito.when(this.userRepository.getById(id))
                .thenReturn(Optional.of(this.expectedUser));

        User actualUser = this.userService.getUserById(id);

        Mockito.verify(this.userRepository, Mockito.times(1)).getById(id);
        Assertions.assertEquals(this.expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(this.expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void testGetAllUsersShouldReturnUsersSuccessfully() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(this.expectedUser);
        Mockito.when(this.userRepository.getAll())
                .thenReturn(expectedUsers);

        List<User> actualUsers = this.userService.getAllUsers();

        Mockito.verify(this.userRepository, Mockito.times(1)).getAll();
        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testDeleteUserByIdShouldDeleteUserSuccessfully() {
        UUID id = UUID.randomUUID();

        this.userService.deleteUserById(id);

        Mockito.verify(this.userRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void testGetUserByUsernameShouldThrowNotFoundException() {
        Mockito.when(this.userRepository.getByUsername(TestParameter.USERNAME))
                .thenReturn(null);

        Assertions.assertThrows(
                NotFoundException.class,
                () -> this.userService.getUserByUsername(TestParameter.USERNAME));
    }

    @Test
    public void testGetUserByUsernameShouldReturnUserSuccessfully() {
        Mockito.when(this.userRepository.getByUsername(TestParameter.USERNAME))
                .thenReturn(this.expectedUser);

        User actualUser = this.userService.getUserByUsername(TestParameter.USERNAME);

        Mockito.verify(this.userRepository, Mockito.times(1)).getByUsername(TestParameter.USERNAME);
        Assertions.assertEquals(this.expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(this.expectedUser.getPassword(), actualUser.getPassword());
    }
}