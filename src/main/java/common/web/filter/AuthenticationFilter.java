package common.web.filter;

import common.factory.service.AuthenticationServiceFactory;
import common.service.interfaces.IAuthenticationService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.StringTokenizer;
@WebFilter(filterName = "AuthenticationFilter", value = "/*")
public class AuthenticationFilter implements Filter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final long EXPIRATION_TIME_IN_MILLISECONDS = 60 * 60 * 1000;
    private final IAuthenticationService authenticationService = AuthenticationServiceFactory.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        long currentTimeInMillis = System.currentTimeMillis();

        if(session == null || session.getAttribute("username") == null) {
            String authenticationCredentials = req.getHeader(AUTHORIZATION_HEADER);
            if(authenticationCredentials == null || !authenticationCredentials.startsWith("Basic ")) throw new IllegalStateException("Bad credentials");

            authenticationCredentials = authenticationCredentials.replaceFirst("Basic ", "");
            String decodedAuthenticationCredentials = new String(Base64.getDecoder().decode(authenticationCredentials), StandardCharsets.UTF_8);

            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthenticationCredentials, ":");
            String username = stringTokenizer.nextToken().trim();
            String rawPassword = stringTokenizer.nextToken().trim();
            boolean isAuthenticationSuccessful = this.authenticationService.isAuthenticationSuccessful(username, rawPassword);

            if(!isAuthenticationSuccessful) throw new IllegalStateException("Bad credentials");

            session = req.getSession(true);
            session.setAttribute("username", username);
        }

        if(session.getCreationTime() + EXPIRATION_TIME_IN_MILLISECONDS < currentTimeInMillis) {
            session.invalidate();
            throw new IllegalStateException("Bad credentials");
        }

        filterChain.doFilter(req, resp);
    }
}