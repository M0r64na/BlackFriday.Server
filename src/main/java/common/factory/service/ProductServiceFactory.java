package common.factory.service;

import application.service.ProductService;
import application.service.interfaces.IProductService;
import application.service.interfaces.IUserService;
import common.factory.repository.ProductRepositoryFactory;
import data.repository.interfaces.IProductRepository;

public final class ProductServiceFactory {
    private static IProductService instance = null;

    private ProductServiceFactory() {}

    public static IProductService getInstance() {
        if(instance == null) {
            IProductRepository productRepository = ProductRepositoryFactory.getInstance();
            IUserService userService = UserServiceFactory.getInstance();
            instance = new ProductService(productRepository, userService);
        }

        return instance;
    }
}