package common.factory.repository;

import data.repository.OrderRepository;
import data.repository.interfaces.IOrderRepository;

public final class OrderRepositoryFactory {
    private static IOrderRepository instance;

    private OrderRepositoryFactory() {}

    public static IOrderRepository getInstance() {
        if(instance == null) instance = new OrderRepository();

        return instance;
    }
}