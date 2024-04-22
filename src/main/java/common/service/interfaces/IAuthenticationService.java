package common.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public interface IAuthenticationService {
    boolean isAuthenticationSuccessful(String username, String rawPassword);
    void logout(HttpSession session, HttpServletResponse resp);
}