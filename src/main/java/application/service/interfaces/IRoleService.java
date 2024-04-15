package application.service.interfaces;

import data.domain.Role;
import data.domain.enums.RoleName;

public interface IRoleService {
    public Role findRoleByName(RoleName name);
    public void initializeRoles();
}