package common.service;

import application.service.interfaces.IUserService;
import common.exception.NotFoundException;
import common.service.interfaces.IAuthorizationService;
import data.domain.User;
import data.domain.enums.RoleName;

public class AuthorizationService implements IAuthorizationService {
    private final IUserService userService;

    public AuthorizationService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasEmployeeRole(String username) {
        if(username == null) return false;

        try {
            User user = this.userService.getUserByUsername(username);
            return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(RoleName.EMPLOYEE));
        }
        catch (NotFoundException ex) {
            return false;
        }
    }
}