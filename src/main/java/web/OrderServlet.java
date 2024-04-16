package web;

import application.service.interfaces.IOrderService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import common.dto.OrderDto;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.service.OrderServiceFactory;
import common.factory.util.GsonFactory;
import common.builder.interfaces.IHttpResponseBuilder;
import common.mapper.IOrderMapper;
import common.web.filter.util.FilterManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "OrderServlet", value = "/orders")
public class OrderServlet extends HttpServlet {
    private final IOrderService orderService = OrderServiceFactory.getInstance();
    private final IHttpResponseBuilder httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final IOrderMapper mapper = IOrderMapper.INSTANCE;
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String idToString = req.getParameter("id");
        String responseToJson;

        if(idToString == null) {
            List<OrderDto> orders = this.orderService.getAllOrders().stream().map(mapper::toRecord).collect(Collectors.toList());
            responseToJson = this.gson.toJson(orders);
        }
        else {
            OrderDto order = mapper.toRecord(this.orderService.getOrderById(UUID.fromString(idToString)));
            responseToJson = this.gson.toJson(order);
        }

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String username = (String) req.getSession().getAttribute("username");

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonObject reqBodyToJsonObject = this.gson.fromJson(reqBody, JsonObject.class);
        JsonObject productNamesAndQuantitiesToJson = reqBodyToJsonObject.getAsJsonObject("productNamesAndQuantities");
        Type typeProductNamesAndQuantities = new TypeToken<Map<String, Integer>>() {}.getType();
        Map<String, Integer> productNamesAndQuantities = gson.fromJson(productNamesAndQuantitiesToJson, typeProductNamesAndQuantities);

        this.orderService.placeOrder(username, productNamesAndQuantities);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }
}