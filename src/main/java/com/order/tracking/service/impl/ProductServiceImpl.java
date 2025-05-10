package com.order.tracking.service.impl;

import com.order.tracking.entity.Product;
import com.order.tracking.exception.ResourceNotFoundException;
import com.order.tracking.repository.ProductRepository;
import com.order.tracking.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// This class implements ProductService and is injected into ProductController via the ProductService interface.
// ProductServiceImpl is registered as a Spring @Service and handles business logic for products.
// The controller only knows about the ProductService interface, not the implementation.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProduct(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public void updateStock(Long id, int quantity) {
        Product product = getProduct(id);
        if (quantity > 0) {
            product.increaseStock(quantity);
        } else {
            product.decreaseStock(-quantity);
        }
        productRepository.save(product);
    }
} 