package com.order.tracking.mapper;

import com.order.tracking.dto.ProductRequestDto;
import com.order.tracking.entity.Category;
import com.order.tracking.entity.Product;
import com.order.tracking.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryService categoryService;

    public Product toEntity(ProductRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Category category = categoryService.getCategory(dto.getCategoryId());
        
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(category)
                .build();
    }

    public void updateEntity(Product product, ProductRequestDto dto) {
        Category category = categoryService.getCategory(dto.getCategoryId());
        
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
    }

    public ProductRequestDto toDto(Product entity) {
        if (entity == null) {
            return null;
        }

        return ProductRequestDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .categoryId(entity.getCategory().getId())
                .build();
    }
} 