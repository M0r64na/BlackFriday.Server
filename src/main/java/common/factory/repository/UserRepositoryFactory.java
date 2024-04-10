package common.factory.repository;

import data.repository.UserRepository;
import data.repository.interfaces.IUserRepository;

public final class UserRepositoryFactory {
    private static IUserRepository instance = null;

    private UserRepositoryFactory() {}

    public static IUserRepository getInstance() {
        if(instance == null) instance = new UserRepository();

        return instance;
    }
}