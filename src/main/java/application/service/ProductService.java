package application.service;

import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import data.model.entity.Product;
import data.model.entity.User;
import data.repository.interfaces.IProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IUserService userService;

    public ProductService(IProductRepository productRepository, IUserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public void createProduct(String name, String description,
                              int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                              String usernameCreatedBy) {
        Product product = new Product(name, description, numberInStock, minPrice, currPrice);

        User createdAndLastModifiedBy = this.userService.getUserByUsername(usernameCreatedBy);
        product.setCreatedBy(createdAndLastModifiedBy);
        product.setLastModifiedBy(createdAndLastModifiedBy);

        this.productRepository.create(product);
    }

    @Override
    public Product updateProduct(String name, String description,
                                 int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                                 String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setName(name);
        product.setDescription(description);
        product.setNumberInStock(numberInStock);
        product = this.updateMinPriceOfProduct(name, minPrice, usernameLastModifiedBy);
        product = this.updateCurrPriceOfProduct(name, currPrice, usernameLastModifiedBy);

        return product;
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return this.productRepository.getById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.getAll();
    }

    @Override
    public Product getProductByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    public void deleteProductById(UUID id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void reduceNumberInStockOfProduct(String name, int quantity, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);

        int newNumberInStock = product.getNumberInStock() - quantity;
        if(newNumberInStock < 0) throw new RuntimeException("Insufficient product quantity");

        product.setNumberInStock(newNumberInStock);

        this.productRepository.update(product);
    }

    private Product updateMinPriceOfProduct(String name, BigDecimal newMinPrice, String usernameLastModifiedBy) {
        if(newMinPrice.compareTo(BigDecimal.ZERO) < 1) throw new RuntimeException("Minimum price of product must be greater than 0");

        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setMinPrice(newMinPrice);

        return this.productRepository.update(product);
    }

    private Product updateCurrPriceOfProduct(String name, BigDecimal newCurrPrice, String usernameLastModifiedBy) {
        Product product = this.getProductByName(name);

        if(newCurrPrice.compareTo(product.getMinPrice()) < 0) throw new RuntimeException("Current price must be greater than or equal to minimum price");

        product.setCurrPrice(newCurrPrice);

        return this.productRepository.update(product);
    }

    private Product getProductAndSetLastModifiedBy(String name, String usernameLastModifiedBy) {
        Product product = this.productRepository.findByName(name);

        User lastModifiedBy = this.userService.getUserByUsername(usernameLastModifiedBy);
        product.setLastModifiedBy(lastModifiedBy);

        return product;
    }
}