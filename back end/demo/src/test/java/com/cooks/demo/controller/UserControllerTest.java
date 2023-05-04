//package com.cooks.demo.controller;
//import com.cooks.demo.DemoApplication;
//import com.cooks.demo.model.Address;
//import com.cooks.demo.model.Chef;
//import com.cooks.demo.model.Customer;
//import com.cooks.demo.model.User;
//import com.cooks.demo.repository.ChefRepository;
//import com.cooks.demo.repository.CustomerRepository;
//import com.cooks.demo.repository.UserRepository;
//import com.cooks.demo.service.CustomerService;
//import com.cooks.demo.service.ObjectService;
//import com.cooks.demo.service.UserService;
//import com.cooks.demo.util.DTOS.RegisterCustomerDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.TypeExcludeFilter;
//import org.springframework.boot.test.autoconfigure.restdocs.RestDocsWebTestClientConfigurationCustomizer;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
////import org.junit.runner.RunWith;
//
//import javax.annotation.security.RunAs;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@ContextConfiguration(classes = RestDocsWebTestClientConfigurationCustomizer.class)
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
//@WebMvcTest(controllers = {UserController.class}, useDefaultFilters = false)
//public class UserControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    WebApplicationContext webApplicationContext;
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    ChefRepository chefRepository;
//
//    @MockBean
//    CustomerRepository customerRepository;
//
//    @MockBean
//    UserService userService;
//
//    ObjectService objectService = new ObjectService();
//
//    @MockBean
//    CustomerService customerService;
//
//    Address address = objectService.getAddress();
//    User user = objectService.getUserObject();
//    Chef chef = objectService.getChefObject();
//    Customer customer = objectService.getCustomer();
//    RegisterCustomerDTO registerCustomerDTO = objectService.getRegisterCustomerDTO();
//
//    @BeforeEach()
//    public void setup()
//    {
//        //Init MockMvc Object and build
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//
//    @Test
//    public void testRegisterCustomerEndPoint() throws Exception{
//        Mockito.when(customerService.registerCustomerUser(registerCustomerDTO)).thenReturn("UserAddedSuccess");
////        String url = "/api/customer/register";
////        ObjectMapper mapper = new ObjectMapper();
////        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
////        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
////        String requestJson=ow.writeValueAsString(this.registerCustomerDTO);
//        mockMvc.perform(post("/api/customer/register")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(registerCustomerDTO)))
//                .andExpect(status().isOk());
//
//    }
//}
