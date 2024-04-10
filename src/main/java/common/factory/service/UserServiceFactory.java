package common.factory.service;

import application.service.RoleService;
import application.service.UserService;
import application.service.interfaces.IUserService;
import common.factory.repository.UserRepositoryFactory;
import data.repository.RoleRepository;
import data.repository.interfaces.IUserRepository;

public final class UserServiceFactory {
    private static IUserService instance = null;

    private UserServiceFactory() {}

    public static IUserService getInstance() {
        if(instance == null) {
            IUserRepository userRepository = UserRepositoryFactory.getInstance();
            instance = new UserService(userRepository, new RoleService(new RoleRepository()));
        }

        return instance;
    }
}