package com.order.tracking.service;

import com.order.tracking.dto.ProductRequestDto;
import com.order.tracking.entity.Category;
import com.order.tracking.entity.Product;
import com.order.tracking.exception.ResourceNotFoundException;
import com.order.tracking.mapper.ProductMapper;
import com.order.tracking.repository.ProductRepository;
import com.order.tracking.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Category category;
    private Product product;
    private ProductRequestDto productRequestDto;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Test Category")
                .description("Test Description")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("100.00"))
                .stock(10)
                .category(category)
                .build();

        productRequestDto = ProductRequestDto.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("100.00"))
                .stock(10)
                .categoryId(1L)
                .build();
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {

        @Test
        @DisplayName("상품을 생성하고 반환한다")
        void createProduct_ShouldReturnCreatedProduct() {
            // given
            when(productRepository.save(any(Product.class))).thenReturn(product);

            // when
            Product createdProduct = productService.createProduct(product);

            // then
            assertThat(createdProduct).isNotNull();
            assertThat(createdProduct.getName()).isEqualTo(product.getName());
            assertThat(createdProduct.getDescription()).isEqualTo(product.getDescription());
            assertThat(createdProduct.getPrice()).isEqualTo(product.getPrice());
            assertThat(createdProduct.getStock()).isEqualTo(product.getStock());
            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {

        @Test
        @DisplayName("상품 ID로 상품을 조회한다")
        void getProduct_ShouldReturnProduct_WhenProductExists() {
            // given
            when(productRepository.findById(1L)).thenReturn(Optional.of(product));

            // when
            Product result = productService.getProduct(1L);

            // then
            assertNotNull(result);
            assertEquals("Test Product", result.getName());
            verify(productRepository).findById(1L);
        }

        @Test
        @DisplayName("존재하지 않는 상품 ID로 조회 시 예외를 발생시킨다")
        void getProduct_ShouldThrowException_WhenProductDoesNotExist() {
            // given
            when(productRepository.findById(999L)).thenReturn(Optional.empty());

            // when & then
            assertThrows(ResourceNotFoundException.class, () -> productService.getProduct(999L));
        }
    }

    @Nested
    @DisplayName("getAllProducts 메서드는")
    class Describe_getAllProducts {

        @Test
        @DisplayName("모든 상품을 조회한다")
        void getAllProducts_ShouldReturnAllProducts() {
            // given
            when(productRepository.findAll()).thenReturn(Arrays.asList(product));

            // when
            List<Product> results = productService.getAllProducts();

            // then
            assertNotNull(results);
            assertEquals(1, results.size());
            assertEquals("Test Product", results.get(0).getName());
            verify(productRepository).findAll();
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {

        @Test
        @DisplayName("상품을 업데이트한다")
        void updateProduct_ShouldUpdateProduct() {
            // given
            Product updatedProduct = Product.builder()
                    .id(1L)
                    .name("Updated Product")
                    .description("Updated Description")
                    .price(new BigDecimal("200.00"))
                    .stock(20)
                    .category(category)
                    .build();

            when(productRepository.findById(1L)).thenReturn(Optional.of(product));
            when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

            // when
            Product result = productService.updateProduct(1L, updatedProduct);

            // then
            assertNotNull(result);
            assertEquals("Updated Product", result.getName());
            assertEquals(new BigDecimal("200.00"), result.getPrice());
            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {

        @Test
        @DisplayName("상품을 삭제한다")
        void deleteProduct_ShouldDeleteProduct() {
            // given
            when(productRepository.findById(1L)).thenReturn(Optional.of(product));
            doNothing().when(productRepository).delete(any(Product.class));

            // when
            productService.deleteProduct(1L);

            // then
            verify(productRepository).delete(any(Product.class));
        }
    }
} 