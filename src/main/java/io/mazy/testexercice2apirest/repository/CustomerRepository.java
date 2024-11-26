package io.mazy.testexercice2apirest.repository;

import io.mazy.testexercice2apirest.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByLastName(String lastName);

}
