package io.mazy.testexercice2apirest.service;

import io.mazy.testexercice2apirest.dto.CustomerDTO;
import io.mazy.testexercice2apirest.entity.Customer;
import io.mazy.testexercice2apirest.dto.CustomerMapper;
import io.mazy.testexercice2apirest.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindCustomerByLastName_found() {
        Customer customer = new Customer();
        customer.setLastName("Mazy");
        when(customerRepository.findByLastName("Mazy")).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(new CustomerDTO(1L, "Toufik", "Mazy", 25));

        Optional<CustomerDTO> result = customerService.findCustomerByLastName("Mazy");
        assertTrue(result.isPresent());
        assertEquals("Mazy", result.get().getLastName());
    }

    @Test
    public void testFindCustomerByLastName_notFound() {
        when(customerRepository.findByLastName("Unknown")).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = customerService.findCustomerByLastName("Unknown");
        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateCustomer_success() {
        Customer customer = new Customer();
        customer.setAge(25);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(new CustomerDTO(1L, "Toufik", "Mazy", 25));

        CustomerDTO result = customerService.updateCustomer(1L, "Toufik", 25, "Magic_Key");
        assertEquals("Toufik", result.getFirstName());
        assertEquals(25, result.getAge());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer_invalidKey() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer(1L, "Toufik", 25, "Wrong_Key");
        });
        assertEquals("La force n'est pas avec toi !", exception.getMessage());
    }

    @Test
    public void testGetAverageAge() {
        Customer customer1 = new Customer();
        customer1.setAge(20);
        Customer customer2 = new Customer();
        customer2.setAge(30);
        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        double averageAge = customerService.getAverageAge();
        assertEquals(25.0, averageAge);
    }
}