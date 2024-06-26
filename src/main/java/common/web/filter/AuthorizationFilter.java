package common.web.filter;

import common.constant.ExceptionMessage;
import common.exception.ForbiddenException;
import common.exception.NotAuthorizedException;
import common.factory.service.AuthorizationServiceFactory;
import common.service.interfaces.IAuthorizationService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AuthorizationFilter implements Filter {
    /* Restricted
    * /users => all
    * /products => without GET
    * /orders => all
    * /campaigns => all
    *
    * get request path + request method
    * check for employee role present for the logged user (session)
     */
    private final IAuthorizationService authorizationService = AuthorizationServiceFactory.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        String username = Optional.ofNullable(session.getAttribute("username")).map(Object::toString).orElse(null);
        boolean hasEmployeeRole = this.authorizationService.hasEmployeeRole(username);

        if(!hasEmployeeRole) throw new ForbiddenException(ExceptionMessage.ACCESS_DENIED_MESSAGE);

        filterChain.doFilter(req, resp);
    }
}