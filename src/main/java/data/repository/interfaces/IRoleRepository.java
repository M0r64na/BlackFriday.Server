package data.repository.interfaces;

import data.domain.Role;
import data.domain.enums.RoleName;
import data.repository.base.IRepository;

public interface IRoleRepository extends IRepository<Role> {
    Role findByName(RoleName name);
}