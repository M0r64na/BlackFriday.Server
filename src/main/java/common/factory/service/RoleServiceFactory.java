package common.factory.service;

import application.service.RoleService;
import application.service.interfaces.IRoleService;
import common.factory.repository.RoleRepositoryFactory;
import data.repository.interfaces.IRoleRepository;

public final class RoleServiceFactory {
    private static IRoleService instance = null;

    private RoleServiceFactory() {}

    public static IRoleService getInstance() {
        if(instance == null) {
            IRoleRepository roleRepository = RoleRepositoryFactory.getInstance();
            instance = new RoleService(roleRepository);
        }

        return instance;
    }
}