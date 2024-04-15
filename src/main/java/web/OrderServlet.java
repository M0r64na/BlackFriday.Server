package web;

import application.service.interfaces.IOrderService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.service.OrderServiceFactory;
import common.factory.util.GsonFactory;
import common.service.interfaces.IHttpResponseBuilderService;
import common.web.filter.util.FilterManager;
import data.domain.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@WebServlet(name = "OrderServlet", value = "/orders")
public class OrderServlet extends HttpServlet {
    private final IOrderService orderService = OrderServiceFactory.getInstance();
    private final IHttpResponseBuilderService httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String idToString = req.getParameter("id");
        String responseToJson;

        if(idToString == null) {
            List<Order> orders = this.orderService.getAllOrders();
            responseToJson = this.gson.toJson(orders);
        }
        else {
            Order order = this.orderService.getOrderById(UUID.fromString(idToString));
            responseToJson = this.gson.toJson(order);
        }

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        Type typeResponseToJson = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> responseToJson = gson.fromJson(req.getReader(), typeResponseToJson);

        String username = (String) responseToJson.get("usernameCreatedBy");

        Type typeProductNamesAndQuantities = new TypeToken<Map<String, Integer>>() {}.getType();
        Map<String, Integer> productNamesAndQuantities = gson.fromJson(gson.toJson(responseToJson.get("productNamesAndQuantities")), typeProductNamesAndQuantities);

        orderService.placeOrder(username, productNamesAndQuantities);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }
}