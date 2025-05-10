package com.order.tracking.service;

import com.order.tracking.dto.CustomerRequestDto;
import com.order.tracking.entity.Customer;
import com.order.tracking.exception.ResourceNotFoundException;
import com.order.tracking.mapper.CustomerMapper;
import com.order.tracking.repository.CustomerRepository;
import com.order.tracking.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Nested
    @DisplayName("고객 생성 테스트")
    class CreateCustomerTest {
        private CustomerRequestDto customerRequestDto;
        private Customer customer;

        @BeforeEach
        void setUp() {
            customerRequestDto = CustomerRequestDto.builder()
                .name("John Doe")
                .email("john@example.com")
                .phone("123-456-7890")
                .build();

            customer = Customer.builder()
                .name("John Doe")
                .email("john@example.com")
                .phone("123-456-7890")
                .build();
        }

        @Test
        @DisplayName("새로운 고객을 생성한다")
        void createCustomer() {
            when(customerRepository.existsByEmail(customerRequestDto.getEmail())).thenReturn(false);
            when(customerMapper.toEntity(customerRequestDto)).thenReturn(customer);
            when(customerRepository.save(any(Customer.class))).thenReturn(customer);

            Customer result = customerService.createCustomer(customerRequestDto);

            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo("John Doe");
            assertThat(result.getEmail()).isEqualTo("john@example.com");
            assertThat(result.getPhone()).isEqualTo("123-456-7890");
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("이미 존재하는 이메일로 고객 생성 시 예외가 발생한다")
        void createCustomerWithExistingEmail() {
            when(customerRepository.existsByEmail(customerRequestDto.getEmail())).thenReturn(true);

            assertThatThrownBy(() -> customerService.createCustomer(customerRequestDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Email already exists");
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Nested
    @DisplayName("고객 조회 테스트")
    class GetCustomerTest {
        private Customer customer;

        @BeforeEach
        void setUp() {
            customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .build();
        }

        @Test
        @DisplayName("ID로 고객을 조회한다")
        void getCustomerById() {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

            Customer result = customerService.getCustomer(1L);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            assertThat(result.getName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("이메일로 고객을 조회한다")
        void getCustomerByEmail() {
            when(customerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(customer));

            Customer result = customerService.getCustomerByEmail("john@example.com");

            assertThat(result).isNotNull();
            assertThat(result.getEmail()).isEqualTo("john@example.com");
        }
    }

    @Nested
    @DisplayName("고객 수정 테스트")
    class UpdateCustomerTest {
        private CustomerRequestDto requestDto;
        private Customer customer;
        private Customer updatedCustomer;

        @BeforeEach
        void setUp() {
            requestDto = CustomerRequestDto.builder()
                .name("홍길동")
                .email("hong@example.com")
                .phone("010-1234-5678")
                .build();

            customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .phone("123-456-7890")
                .build();

            updatedCustomer = Customer.builder()
                .id(1L)
                .name("홍길동")
                .email("hong@example.com")
                .phone("010-1234-5678")
                .build();
        }

        @Test
        @DisplayName("고객 정보를 수정한다")
        void updateCustomer() {
            when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
            when(customerMapper.toEntity(requestDto)).thenReturn(updatedCustomer);
            when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

            Customer result = customerService.updateCustomer(1L, requestDto);

            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(requestDto.getName());
            assertThat(result.getEmail()).isEqualTo(requestDto.getEmail());
            assertThat(result.getPhone()).isEqualTo(requestDto.getPhone());
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("존재하지 않는 고객 수정 시 예외가 발생한다")
        void updateNonExistentCustomer() {
            when(customerRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> customerService.updateCustomer(1L, requestDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found with id : '1'");
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Nested
    @DisplayName("고객 목록 조회 테스트")
    class GetAllCustomersTest {
        @Test
        @DisplayName("고객 목록 조회 성공")
        void getAllCustomers_Success() {
            Customer customer1 = Customer.builder()
                .id(1L)
                .name("홍길동")
                .build();

            Customer customer2 = Customer.builder()
                .id(2L)
                .name("김철수")
                .build();

            when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

            List<Customer> customers = customerService.getAllCustomers();

            assertThat(customers).isNotNull();
            assertThat(customers).hasSize(2);
            assertThat(customers.get(0).getName()).isEqualTo("홍길동");
            assertThat(customers.get(1).getName()).isEqualTo("김철수");
            verify(customerRepository).findAll();
        }
    }
} 