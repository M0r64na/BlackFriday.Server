package common.service.interfaces;

public interface IAuthenticationService {
    boolean isAuthenticationSuccessful(String username, String rawPassword);
}