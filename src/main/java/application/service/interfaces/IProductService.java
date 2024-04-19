package application.service.interfaces;

import data.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IProductService {
    void createProduct(String name, String description,
                       int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                       String usernameCreatedBy);
    Product updateProduct(String prevName, String name, String description,
                          int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                          String usernameLastModifiedBy);
    Product getProductById(UUID id);
    List<Product> getAllProducts();
    Product getProductByName(String name);
    void deleteProductById(UUID id);
    void reduceNumberInStockOfProduct(String name, int quantity, String usernameLastModifiedBy);
}