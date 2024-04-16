package web;

import application.service.interfaces.IProductService;
import com.google.gson.Gson;
import common.dto.ProductDto;
import common.factory.service.HttpResponseBuilderFactory;
import common.factory.service.ProductServiceFactory;
import common.factory.util.GsonFactory;
import common.builder.interfaces.IHttpResponseBuilder;
import common.mapper.IProductMapper;
import common.web.filter.util.FilterManager;
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
    private final IProductMapper mapper = IProductMapper.INSTANCE;
    private final Gson gson = GsonFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isPartOfCampaign = req.getParameter("campaign");
        if(isPartOfCampaign != null && isPartOfCampaign.equals("true")) {
            req.getRequestDispatcher("campaigns").forward(req, resp);
            return;
        }

        String name = req.getParameter("name");
        String responseToJson;

        if(name == null) {
            List<ProductDto> products = this.productService.getAllProducts().stream().map(mapper::toRecord).collect(Collectors.toList());
            responseToJson = this.gson.toJson(products);
        }
        else {
            ProductDto product = mapper.toRecord(this.productService.getProductByName(name));
            responseToJson = this.gson.toJson(product);
        }

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String username = (String) req.getSession().getAttribute("username");

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ProductDto product = this.gson.fromJson(reqBody, ProductDto.class);

        this.productService.createProduct(product.name(), product.description(), product.numberInStock(),
                product.minPrice(), product.currPrice(), username);

        this.httpResponseBuilder.buildHttResponse(resp, "", HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        String username = (String) req.getSession().getAttribute("username");

        String reqBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ProductDto product = this.gson.fromJson(reqBody, ProductDto.class);

        product = mapper.toRecord(this.productService
                .updateProduct(product.name(), product.description(), product.numberInStock(),
                product.minPrice(), product.currPrice(), username));
        String responseToJson = this.gson.toJson(product);

        this.httpResponseBuilder.buildHttResponse(resp, responseToJson);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilterManager.process(req, resp);

        UUID id = UUID.fromString(req.getParameter("id"));
        this.productService.deleteProductById(id);

        this.httpResponseBuilder.buildHttResponse(resp, "");
    }
}