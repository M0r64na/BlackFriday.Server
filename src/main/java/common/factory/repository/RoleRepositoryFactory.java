package common.factory.repository;

import data.repository.RoleRepository;
import data.repository.interfaces.IRoleRepository;

public final class RoleRepositoryFactory {
    private static IRoleRepository instance = null;

    private RoleRepositoryFactory() {}

    public static IRoleRepository getInstance() {
        if(instance == null) instance = new RoleRepository();

        return instance;
    }
}