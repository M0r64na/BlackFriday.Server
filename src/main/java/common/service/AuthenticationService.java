package common.service;

import application.service.interfaces.IUserService;
import application.util.PasswordEncoder;
import common.service.interfaces.IAuthenticationService;
import data.model.entity.User;

public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;

    public AuthenticationService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isAuthenticationSuccessful(String username, String rawPassword) {
        /*
        * Basic username:password
        * username:password Base64 encoding
        * 1) decode
        * 2) extract username
        * 3) extract password
        * 4) verify
        * 5) session management
        */
        if(username == null || rawPassword == null) return false;

        User user = this.userService.getUserByUsername(username);
        if(user == null) return false;

        return PasswordEncoder.verifyPasswords(user.getPassword(), rawPassword);
    }
}