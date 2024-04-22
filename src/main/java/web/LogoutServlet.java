package web;

import common.factory.service.AuthenticationServiceFactory;
import common.service.interfaces.IAuthenticationService;
import common.web.filter.util.FilterManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    private final IAuthenticationService authenticationService = AuthenticationServiceFactory.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp, false);

        this.authenticationService.logout(req.getSession(false), resp);
    }
}