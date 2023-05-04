package com.cooks.demo.service;

import com.cooks.demo.model.Address;
import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Customer;
import com.cooks.demo.model.User;
import com.cooks.demo.repository.ChefRepository;
import com.cooks.demo.repository.CustomerRepository;
import com.cooks.demo.repository.UserRepository;
import com.cooks.demo.util.DTOS.RegisterCustomerDTO;
import com.cooks.demo.util.DTOS.UserDetailDTO;
import org.junit.jupiter.api.BeforeAll;
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
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;


    ObjectService objectService = new ObjectService();

    User user = objectService.getUserObject();
    Chef chef = objectService.getChefObject();
    Customer customer = objectService.getCustomer();
    UserDetailDTO userDetailDTO = objectService.getUserDetailDTO();

    @BeforeAll
    public void initialize() {
        this.user = new User(1, "name", "name@gmail.com", "Male", 9164560226L, "password", "ROLE_CHEF");
        this.chef = new Chef(1, "BCA", 5.0D, 1, false, user);

        this.userDetailDTO = userDetailDTO.builder()
                .name("name")
                .email("name@gmail.com")
                .gender("Male")
                .phoneNumber(1234567890L)
                .userType("ROLE_CHEF")
                .password("password")
                .verifyPassword("password")
                .build();
    }

    @Test
    public void testGetUserFromDTO() {
        assertSame(this.user.getClass(), userService.getUserFromDTO(this.userDetailDTO).getClass());
    }

    @Test
    public void testRegisterBasicUserDetails() {
        doReturn(user).when(userRepository).save(user);
        assertSame(user.getClass(), userService.registerBasicUserDetails(user).getClass());

    }


}