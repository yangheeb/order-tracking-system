package com.order.tracking.service;

import com.order.tracking.entity.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> searchProducts(String name);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    void updateStock(Long id, int quantity);
} 