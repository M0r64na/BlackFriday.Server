package application.service.interfaces;

import data.model.entity.Order;

import java.util.*;

public interface IOrderService {
    void placeOrder(String username, Map<String, Integer> productNamesAndQuantities);
    Optional<Order> getOrderById(UUID id);
    List<Order> getAllOrders();
}