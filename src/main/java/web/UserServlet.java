package web;

import application.service.interfaces.IUserService;
import common.factory.service.UserServiceFactory;
import com.google.gson.Gson;
import common.factory.util.GsonFactory;
import data.model.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private final IUserService userService = UserServiceFactory.getInstance();
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User raw = gson.fromJson(reqBody, User.class);

        this.userService.createUser(raw.getUsername(), raw.getPassword());

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }
}