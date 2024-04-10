package common.factory.repository;

import data.repository.ProductRepository;
import data.repository.interfaces.IProductRepository;

public final class ProductRepositoryFactory {
    private static IProductRepository instance;

    private ProductRepositoryFactory() {}

    public static IProductRepository getInstance() {
        if(instance == null) instance = new ProductRepository();

        return instance;
    }
}