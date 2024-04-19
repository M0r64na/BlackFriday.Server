package application.service;

import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import common.exception.ConflictException;
import common.exception.NotFoundException;
import data.domain.Product;
import data.domain.User;
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
        User createdAndLastModifiedBy = this.userService.getUserByUsername(usernameCreatedBy);
        if(createdAndLastModifiedBy == null) throw new NotFoundException("No such user found");

        Product product = new Product(name, description, numberInStock, minPrice, currPrice);
        product.setCreatedBy(createdAndLastModifiedBy);
        product.setLastModifiedBy(createdAndLastModifiedBy);

        this.productRepository.create(product);
    }

    @Override
    public Product updateProduct(String prevName, String name, String description,
                                 int numberInStock, BigDecimal minPrice, BigDecimal currPrice,
                                 String usernameLastModifiedBy) {
        Product product = this.updateMinPriceOfProduct(prevName, minPrice, usernameLastModifiedBy);
        product = this.updateCurrPriceOfProduct(prevName, currPrice, usernameLastModifiedBy);
        product.setName(name);
        product.setDescription(description);
        product.setNumberInStock(numberInStock);

        return this.productRepository.update(product);
    }

    @Override
    public Product getProductById(UUID id) {
        Optional<Product> product = this.productRepository.getById(id);
        if(product.isEmpty()) throw new NotFoundException("No such product found");

        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.getAll();
    }

    @Override
    public Product getProductByName(String name) {
        Product product = this.productRepository.findByName(name);
        if(product == null) throw new NotFoundException("No such product found");

        return product;
    }

    @Override
    public void deleteProductById(UUID id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void reduceNumberInStockOfProduct(String name, int quantity, String usernameLastModifiedBy) {
        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);

        int newNumberInStock = product.getNumberInStock() - quantity;
        if(newNumberInStock < 0) throw new ConflictException("Insufficient product quantity");

        product.setNumberInStock(newNumberInStock);

        this.productRepository.update(product);
    }

    private Product updateMinPriceOfProduct(String name, BigDecimal newMinPrice, String usernameLastModifiedBy) {
        if(newMinPrice.compareTo(BigDecimal.ZERO) < 1) throw new ConflictException("Minimum price of product must be greater than 0");

        Product product = this.getProductAndSetLastModifiedBy(name, usernameLastModifiedBy);
        product.setMinPrice(newMinPrice);

        return this.productRepository.update(product);
    }

    private Product updateCurrPriceOfProduct(String name, BigDecimal newCurrPrice, String usernameLastModifiedBy) {
        Product product = this.getProductByName(name);
        if(product == null) throw new NotFoundException("No such product found");
        if(newCurrPrice.compareTo(product.getMinPrice()) < 0) throw new ConflictException("Current price must be greater than or equal to minimum price");

        product.setCurrPrice(newCurrPrice);

        return this.productRepository.update(product);
    }

    private Product getProductAndSetLastModifiedBy(String name, String usernameLastModifiedBy) {
        User lastModifiedBy = this.userService.getUserByUsername(usernameLastModifiedBy);
        if(lastModifiedBy == null) throw new NotFoundException("No such user found");

        Product product = this.getProductByName(name);
        product.setLastModifiedBy(lastModifiedBy);

        return product;
    }
}