package common.factory.service;

import application.service.interfaces.IUserService;
import common.service.AuthenticationService;
import common.service.interfaces.IAuthenticationService;

public final class AuthenticationServiceFactory {
    private static IAuthenticationService instance = null;

    private AuthenticationServiceFactory() {}

    public static IAuthenticationService getInstance() {
        if(instance == null) {
            IUserService userService = UserServiceFactory.getInstance();
            instance = new AuthenticationService(userService);
        }

        return instance;
    }
}