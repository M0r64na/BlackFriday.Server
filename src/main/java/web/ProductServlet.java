package web;

import application.service.interfaces.IProductService;
import com.google.gson.Gson;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.service.ProductServiceFactory;
import common.factory.util.GsonFactory;
import common.service.interfaces.IHttpResponseBuilder;
import common.web.filter.util.FilterManager;
import data.model.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private final IProductService productService = ProductServiceFactory.getInstance();
    private final IHttpResponseBuilder httpResponseBuilder = HttpResponseBuilderFactory.getInstance();
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String responseToJson;

        if(name == null) {
            List<Product> products = this.productService.getAllProducts();
            responseToJson = this.gson.toJson(products);
        }
        else {
            Product product = this.productService.getProductByName(name);
            responseToJson = this.gson.toJson(product);
        }

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Product product = this.gson.fromJson(reqBody, Product.class);

        this.productService.createProduct(product.getName(), product.getDescription(), product.getNumberInStock(),
                product.getMinPrice(), product.getCurrPrice(), product.getCreatedBy().getUsername());

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Product product = this.gson.fromJson(reqBody, Product.class);

        product = this.productService.updateProduct(product.getName(), product.getDescription(), product.getNumberInStock(),
                product.getMinPrice(), product.getCurrPrice(), product.getLastModifiedBy().getUsername());
        String responseToJson = this.gson.toJson(product);

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        UUID id = UUID.fromString(req.getParameter("id"));
        this.productService.deleteProductById(id);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_OK);
    }
}