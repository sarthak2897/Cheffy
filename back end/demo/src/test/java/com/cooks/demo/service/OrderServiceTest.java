//package com.cooks.demo.service;
//
//import com.cooks.demo.model.Customer;
//import com.cooks.demo.model.Order;
//import com.cooks.demo.model.User;
//import com.cooks.demo.repository.CustomerRepository;
//import com.cooks.demo.repository.OrderRepository;
//import com.cooks.demo.util.DTOS.RequestOrderDTO;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.doReturn;
//
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class OrderServiceTest {
//
//    @Mock
//    OrderRepository orderRepository;
//
//    @Mock
//    CustomerRepository customerRepository;
//
//    @Autowired
//    OrderService orderService;
//
//    ObjectService objectService = new ObjectService();
//
//
////    Order order = orderService.
//
//    Customer customer = objectService.getCustomer();
//    Order order = objectService.getOrder();
//    RequestOrderDTO requestOrderDTO = objectService.getRequestOrderDTO();
////    @BeforeAll
////    public void setUp(){
////        requestOrderDTO.setMealType("ytf");
////        requestOrderDTO.setFoodPreference("ihg");
////        requestOrderDTO.setGenderPreference("Male");
////        requestOrderDTO.setChefTier("Tierv1");
////        requestOrderDTO.setAmount(76765L);
////        requestOrderDTO.setIngredients(true);
////        requestOrderDTO.setNoOfHobs(5);
////        requestOrderDTO.setNoOfPeople(5);
////        requestOrderDTO.setOccasion("h");
////        requestOrderDTO.setOven(true);
////        requestOrderDTO.setTypeOfHob("jhg");
////        requestOrderDTO.setCuisine("Kashmiri");
////        requestOrderDTO.setCustomerID(1);
////        requestOrderDTO.setStatus("ghfg");
////        order = orderService.parseRequestToOrder(requestOrderDTO);
////        customer.setUser(new User());
////        customer.getUser().setEmail("Hello@gmail.com");
////        customer.setId(1);
////    }
//    @Test
//    public void postOrdersTest() throws Exception {
//        doReturn(Optional.of(customer)).when(customerRepository).findById(requestOrderDTO.getCustomerID());
//        doReturn(order).when(orderRepository).save(order);
//        Assertions.assertSame(order.getClass(), orderService.postOrders(requestOrderDTO).getClass());
//    }
//}
