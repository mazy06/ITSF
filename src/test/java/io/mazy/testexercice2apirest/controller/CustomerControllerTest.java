package io.mazy.testexercice2apirest.controller;


import io.mazy.testexercice2apirest.dto.CustomerDTO;
import io.mazy.testexercice2apirest.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetCustomerByLastName_found() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO(1L, "Toufik", "Mazy", 34);
        Mockito.when(customerService.findCustomerByLastName("Mazy")).thenReturn(Optional.of(customerDTO));

        mockMvc.perform(get("/customers?lastName=Mazy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName").value("Mazy"))
                .andExpect(jsonPath("$.age").value(34));
    }

    @Test
    public void testGetCustomerByLastName_notFound() throws Exception {
        Mockito.when(customerService.findCustomerByLastName(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/customers?lastName=Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomer_success() throws Exception {
        CustomerDTO updatedCustomer = new CustomerDTO(1L, "Gérard", "Updated", 25); // Simulez le retour attendu
        Mockito.when(customerService.updateCustomer(1L, "Gérard", 25, "Magic_Key")).thenReturn(updatedCustomer);

        mockMvc.perform(put("/customers/1")
                        .header("key", "Magic_Key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"Gérard\",\"age\":25}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mise à jour réussie !"));
    }


    @Test
    public void testUpdateCustomer_invalidKey() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("La force n'est pas avec toi !"))
                .when(customerService).updateCustomer(1L, "Gérard", 25, "Wrong_Key");

        mockMvc.perform(put("/customers/1")
                        .header("key", "Wrong_Key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"first_name\":\"Gérard\",\"age\":25}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La force n'est pas avec toi !"));
    }


    @Test
    public void testGetAverageAge() throws Exception {
        Mockito.when(customerService.getAverageAge()).thenReturn(26.5);

        mockMvc.perform(get("/customers/age/average"))
                .andExpect(status().isOk())
                .andExpect(content().string("26.5"));
    }
}