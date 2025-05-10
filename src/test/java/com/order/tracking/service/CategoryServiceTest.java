package com.order.tracking.service;

import com.order.tracking.entity.Category;
import com.order.tracking.exception.ResourceNotFoundException;
import com.order.tracking.mapper.CategoryMapper;
import com.order.tracking.repository.CategoryRepository;
import com.order.tracking.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Test Category")
                .description("Test Description")
                .build();
    }

    @Nested
    @DisplayName("createCategory 메서드는")
    class Describe_createCategory {

        @Test
        @DisplayName("카테고리를 생성하고 반환한다")
        void createCategory_ShouldReturnCreatedCategory() {
            // given
            when(categoryRepository.existsByName(anyString())).thenReturn(false);
            when(categoryRepository.save(any(Category.class))).thenReturn(category);

            // when
            Category result = categoryService.createCategory(category);

            // then
            assertNotNull(result);
            assertEquals("Test Category", result.getName());
            assertEquals("Test Description", result.getDescription());
            verify(categoryRepository).save(any(Category.class));
        }

        @Test
        @DisplayName("이미 존재하는 카테고리 이름으로 생성 시 예외를 발생시킨다")
        void createCategory_ShouldThrowException_WhenNameExists() {
            // given
            when(categoryRepository.existsByName(anyString())).thenReturn(true);

            // when & then
            assertThrows(IllegalStateException.class, () -> categoryService.createCategory(category));
            verify(categoryRepository, never()).save(any(Category.class));
        }
    }

    @Nested
    @DisplayName("getCategory 메서드는")
    class Describe_getCategory {

        @Test
        @DisplayName("카테고리 ID로 카테고리를 조회한다")
        void getCategory_ShouldReturnCategory_WhenCategoryExists() {
            // given
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

            // when
            Category result = categoryService.getCategory(1L);

            // then
            assertNotNull(result);
            assertEquals("Test Category", result.getName());
            verify(categoryRepository).findById(1L);
        }

        @Test
        @DisplayName("존재하지 않는 카테고리 ID로 조회 시 예외를 발생시킨다")
        void getCategory_ShouldThrowException_WhenCategoryDoesNotExist() {
            // given
            when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

            // when & then
            assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategory(999L));
        }
    }

    @Nested
    @DisplayName("getAllCategories 메서드는")
    class Describe_getAllCategories {

        @Test
        @DisplayName("모든 카테고리를 조회한다")
        void getAllCategories_ShouldReturnAllCategories() {
            // given
            when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

            // when
            List<Category> results = categoryService.getAllCategories();

            // then
            assertNotNull(results);
            assertEquals(1, results.size());
            assertEquals("Test Category", results.get(0).getName());
            verify(categoryRepository).findAll();
        }
    }

    @Nested
    @DisplayName("updateCategory 메서드는")
    class Describe_updateCategory {

        @Test
        @DisplayName("카테고리를 업데이트한다")
        void updateCategory_ShouldUpdateCategory() {
            // given
            Category updatedCategory = Category.builder()
                    .id(1L)
                    .name("Updated Category")
                    .description("Updated Description")
                    .build();

            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

            // when
            Category result = categoryService.updateCategory(1L, updatedCategory);

            // then
            assertNotNull(result);
            assertEquals("Updated Category", result.getName());
            assertEquals("Updated Description", result.getDescription());
            verify(categoryRepository).save(any(Category.class));
        }
    }

    @Nested
    @DisplayName("deleteCategory 메서드는")
    class Describe_deleteCategory {

        @Test
        @DisplayName("카테고리를 삭제한다")
        void deleteCategory_ShouldDeleteCategory() {
            // given
            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
            doNothing().when(categoryRepository).delete(any(Category.class));

            // when
            categoryService.deleteCategory(1L);

            // then
            verify(categoryRepository).delete(any(Category.class));
        }
    }
} 