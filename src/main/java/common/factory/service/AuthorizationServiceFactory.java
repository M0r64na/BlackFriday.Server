package common.factory.service;

import application.service.interfaces.IUserService;
import common.service.AuthorizationService;
import common.service.interfaces.IAuthorizationService;

public final class AuthorizationServiceFactory {
    private static IAuthorizationService instance;

    private AuthorizationServiceFactory() {}

    public static IAuthorizationService getInstance() {
        if(instance == null) {
            IUserService userService = UserServiceFactory.getInstance();
            instance = new AuthorizationService(userService);
        }

        return instance;
    }
}