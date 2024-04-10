package common.factory.service;

import application.service.OrderService;
import application.service.interfaces.IOrderService;
import application.service.interfaces.IProductService;
import application.service.interfaces.IStatusService;
import application.service.interfaces.IUserService;
import common.factory.repository.OrderRepositoryFactory;
import data.repository.interfaces.IOrderRepository;

public final class OrderServiceFactory {
    private static IOrderService instance = null;

    private OrderServiceFactory() {}

    public static IOrderService getInstance() {
        if(instance == null) {
            IOrderRepository orderRepository = OrderRepositoryFactory.getInstance();
            IUserService userService = UserServiceFactory.getInstance();
            IStatusService statusService = StatusServiceFactory.getInstance();
            IProductService productService = ProductServiceFactory.getInstance();
            instance = new OrderService(orderRepository, userService, statusService, productService);
        }

        return instance;
    }
}