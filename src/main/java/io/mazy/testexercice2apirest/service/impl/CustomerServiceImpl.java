package io.mazy.testexercice2apirest.service.impl;

import io.mazy.testexercice2apirest.dto.CustomerDTO;
import io.mazy.testexercice2apirest.dto.CustomerMapper;
import io.mazy.testexercice2apirest.entity.Customer;
import io.mazy.testexercice2apirest.repository.CustomerRepository;
import io.mazy.testexercice2apirest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    private static final String MAGIC_KEY = "Magic_Key";


    @Override
    public Optional<CustomerDTO> findCustomerByLastName(String lastname) {
        return customerRepository.findByLastName(lastname).map(customerMapper::toDTO);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, String firstName, Integer age, String key) {

        if (!MAGIC_KEY.equals(key)) {
            throw new IllegalArgumentException("La force n'est pas avec toi !");
        }

        if (age < 18) {
            throw new IllegalArgumentException("Le client doit être majeur!");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Le client n'existe pas "));

        customer.setFirstName(firstName);
        customer.setAge(age);

        customer = customerRepository.save(customer);

        return customerMapper.toDTO(customer);
    }

    @Override
    public double getAverageAge() {
        return customerRepository.findAll().stream().mapToInt(Customer::getAge).average().orElse(0.0);
    }
}
