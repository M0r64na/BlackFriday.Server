package common.web.listener;

import application.service.interfaces.IRoleService;
import application.service.interfaces.IStatusService;
import application.service.interfaces.IUserService;
import common.factory.service.RoleServiceFactory;
import common.factory.service.StatusServiceFactory;
import common.factory.service.UserServiceFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {
    private final IRoleService roleService = RoleServiceFactory.getInstance();
    private final IUserService userService = UserServiceFactory.getInstance();
    private final IStatusService statusService = StatusServiceFactory.getInstance();


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.roleService.initializeRoles();
        this.userService.initializeUsers();
        this.statusService.initializeStatuses();
    }
}