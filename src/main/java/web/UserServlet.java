package web;

import application.service.interfaces.IUserService;
import common.dto.UserDto;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.service.UserServiceFactory;
import com.google.gson.Gson;
import common.factory.util.GsonFactory;
import common.builder.interfaces.IHttpResponseBuilder;
import common.mapper.IUserMapper;
import common.web.filter.util.FilterManager;
import data.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private final IUserService userService = UserServiceFactory.getInstance();
    private final IHttpResponseBuilder httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final IUserMapper mapper = IUserMapper.INSTANCE;
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String username = req.getParameter("username");
        String responseToJson;

        if(username == null) {
            List<UserDto> users = this.userService.getAllUsers().stream().map(mapper::toRecord).collect(Collectors.toList());
            responseToJson = this.gson.toJson(users);
        }
        else {
            UserDto user = mapper.toRecord(this.userService.getUserByUsername(username));
            responseToJson = this.gson.toJson(user);
        }

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User user = gson.fromJson(reqBody, User.class);

        this.userService.createUser(user.getUsername(), user.getPassword());

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User user = gson.fromJson(reqBody, User.class);

        user = this.userService.updateUser(user.getUsername(), user.getPassword());
        String responseToJson = this.gson.toJson(mapper.toRecord(user));

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        UUID id = UUID.fromString(req.getParameter("id"));
        this.userService.deleteUserById(id);

        this.httpResponseBuilder.buildHttResponse(resp, "");
    }
}