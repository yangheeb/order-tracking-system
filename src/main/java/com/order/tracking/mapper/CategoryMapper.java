package com.order.tracking.mapper;

import com.order.tracking.dto.CategoryRequestDto;
import com.order.tracking.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    
    public Category toEntity(CategoryRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public CategoryRequestDto toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        return CategoryRequestDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public void updateEntity(Category category, CategoryRequestDto dto) {
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }
} 