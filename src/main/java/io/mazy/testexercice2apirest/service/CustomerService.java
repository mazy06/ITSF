package io.mazy.testexercice2apirest.service;

import io.mazy.testexercice2apirest.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface CustomerService {

    Optional<CustomerDTO> findCustomerByLastName(String lastname);

    CustomerDTO updateCustomer(Long id, String firstName, Integer age, String key);

    double getAverageAge();

}
