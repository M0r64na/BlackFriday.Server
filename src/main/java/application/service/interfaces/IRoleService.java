package application.service.interfaces;

import data.model.entity.Role;
import data.model.entity.enums.RoleName;

public interface IRoleService {
    public Role findRoleByName(RoleName name);
    public void initializeRoles();
}