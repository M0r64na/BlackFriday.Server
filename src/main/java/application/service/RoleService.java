package application.service;

import application.service.interfaces.IRoleService;
import data.domain.Role;
import data.domain.enums.RoleName;
import data.repository.interfaces.IRoleRepository;

public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(RoleName name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public void initializeRoles() {
        if(!this.roleRepository.getAll().isEmpty()) return;

        this.roleRepository.create(new Role(RoleName.CLIENT));
        this.roleRepository.create(new Role(RoleName.EMPLOYEE));
    }
}