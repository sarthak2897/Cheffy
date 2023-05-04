package com.cooks.demo.service;
import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Customer;
import com.cooks.demo.model.User;
import com.cooks.demo.repository.CustomerRepository;
import com.cooks.demo.repository.UserRepository;
import com.cooks.demo.util.DTOS.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {

    ObjectService objectService = new ObjectService();

    User user = objectService.getUserObject();
    Chef chef = objectService.getChefObject();
    UserDetailDTO userDetailDTO = objectService.getUserDetailDTO();
    AddressDTO addressDTO = objectService.getAddressDTO();
    RegisterCustomerDTO registerCustomerDTO = objectService.getRegisterCustomerDTO();
    Customer customer = objectService.getCustomer();

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    UserRepository userRepository;


    @Test
    public void testGetCustomerFromDTO() {
        assertSame(this.customer.getClass(), customerService.getCustomerFromDTO(this.registerCustomerDTO, this.user).getClass());
    }

    @Test
    public void testRegisterCustomer() {
        when(customerRepository.save(this.customer)).thenReturn(this.customer);
        assertEquals("You have been successfully registered as customer.", customerService.registerCustomer(registerCustomerDTO, this.user));
    }
    @Test
    public void testRegisterCustomerWithDifferentPasswords() throws Exception {
        RegisterCustomerDTO registerCustomerDTOWithDifferentPasswords = new RegisterCustomerDTO(userDetailDTO, addressDTO);
        registerCustomerDTOWithDifferentPasswords.getUserDetailDTO().setPassword("password");
        registerCustomerDTOWithDifferentPasswords.getUserDetailDTO().setPassword("different password");
        assertThrows(Exception.class, () -> customerService.registerCustomerUser(registerCustomerDTOWithDifferentPasswords));
    }
    @Test
    public void testRegisterUserWithChefAndSamePasswords() throws Exception {
        RegisterCustomerDTO registerCustomerDTOWithSamePasswords = new RegisterCustomerDTO(userDetailDTO, addressDTO);
        registerCustomerDTOWithSamePasswords.getUserDetailDTO().setPassword("password");
        registerCustomerDTOWithSamePasswords.getUserDetailDTO().setPassword("password");
        doReturn(this.user).when(userRepository).save(this.user);
        doReturn(this.customer).when(customerRepository).save(this.customer);
        assertEquals("You have been successfully registered as customer.",customerService.registerCustomerUser(registerCustomerDTOWithSamePasswords));
    }
}
