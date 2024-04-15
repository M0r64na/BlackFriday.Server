package application.service.interfaces;

import data.domain.Order;

import java.util.*;

public interface IOrderService {
    void placeOrder(String username, Map<String, Integer> productNamesAndQuantities);
    Order getOrderById(UUID id);
    List<Order> getAllOrders();
}