package common.service;

import application.service.interfaces.IUserService;
import common.service.interfaces.IAuthorizationService;
import data.model.entity.User;
import data.model.entity.enums.RoleName;

public class AuthorizationService implements IAuthorizationService {
    private final IUserService userService;

    public AuthorizationService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasEmployeeRole(String username) {
        if(username == null) return false;

        User user = this.userService.getUserByUsername(username);
        if(user == null) return false;

        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleName.EMPLOYEE));
    }
}