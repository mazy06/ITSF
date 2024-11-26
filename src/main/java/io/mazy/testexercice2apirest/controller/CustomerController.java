package io.mazy.testexercice2apirest.controller;

import io.mazy.testexercice2apirest.dto.CustomerDTO;
import io.mazy.testexercice2apirest.exception.ErrorResponse;
import io.mazy.testexercice2apirest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getCustomerByLastName(@RequestParam("lastName") String lastName) {
        Optional<CustomerDTO> customerDTO = customerService.findCustomerByLastName(lastName);

        if (customerDTO.isPresent()) {
            return ResponseEntity.ok(customerDTO.get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Customer non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id,
                                                 @RequestHeader Map<String, String> headers,
                                                 @RequestBody Map<String, Object> payload) {

        String key = headers.get("key");
        String firstName = payload.get("first_name").toString();
        Integer age = Integer.valueOf(payload.get("age").toString());

        try {
            customerService.updateCustomer(id, firstName, age, key);
            return ResponseEntity.ok("Mise à jour réussie !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/age/average")
    public ResponseEntity<String> getAverageAge() {
        double averageAge = customerService.getAverageAge();
        return ResponseEntity.ok(String.valueOf(averageAge));
    }

}
