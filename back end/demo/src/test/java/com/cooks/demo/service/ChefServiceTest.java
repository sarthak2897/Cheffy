package com.cooks.demo.service;

import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Order;
import com.cooks.demo.model.User;
import com.cooks.demo.repository.ChefRepository;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.repository.UserRepository;
import com.cooks.demo.util.DTOS.ChefDetailDTO;
import com.cooks.demo.util.DTOS.RegisterChefDTO;
import com.cooks.demo.util.DTOS.UserDetailDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChefServiceTest {

    ObjectService objectService = new ObjectService();

    User user = objectService.getUserObject();
    Chef chef = objectService.getChefObject();
    UserDetailDTO userDetailDTO = objectService.getUserDetailDTO();
    ChefDetailDTO chefDetailDTO = objectService.getChefDetailDTO();
    RegisterChefDTO registerChefDTO = objectService.getRegisterChefDTO();
    Order order = objectService.getOrder();

    @Autowired
    ChefService chefService;

    @MockBean
    ChefRepository chefRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    OrderRepository orderRepository;

    @Test
    public void testTire1FromExperience() {
        assertEquals(1, chefService.setTierOnExperience(5.0));
    }

    @Test
    public void testTire2FromExperience() {
        assertEquals(2, chefService.setTierOnExperience(4.0));
    }

    @Test
    public void testTire3FromExperience() {
        assertEquals(3, chefService.setTierOnExperience(1.0));
    }
    @Test
    public void testGetChefFromDTO() {
        assertSame(this.chef.getClass(), chefService.getChefFromDTO(this.registerChefDTO, this.user).getClass());
    }
    @Test
    public void testConvertOrderToChefOrdersDTO(){
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        orders.add(order);
        assertEquals(2, chefService.convertOrderToChefOrdersDTO(orders).size());
    }

    @Test
    public void testGetAllOrdersOfChef() throws Exception{
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        orders.add(order);
        doReturn(orders).when(orderRepository).getAllOrdersOfChef(1);
        assertEquals(2, chefService.getAllOrdersOfChef(1).size());
    }

    @Test
    public void testGetAllChefs(){
        List<Chef> chefs = new ArrayList<Chef>();
        chefs.add(chef);
        chefs.add(chef);
        doReturn(chefs).when(chefRepository).findAll();
        assertEquals(2, chefService.getAllChefs().size());
    }

    @Test
    public void testRegisterChef() throws Exception {
        when(chefRepository.save(this.chef)).thenReturn(this.chef);
        assertEquals("You have been successfully registered as chef.", chefService.registerChef(registerChefDTO, this.user));
    }

    @Test
    public void testRegisterUserChefWithDifferentPasswords() throws Exception {
        RegisterChefDTO registerChefDTOWithDifferentPasswords = new RegisterChefDTO(userDetailDTO, chefDetailDTO);
        registerChefDTOWithDifferentPasswords.getUserDetailDTO().setPassword("password");
        registerChefDTOWithDifferentPasswords.getUserDetailDTO().setPassword("different password");
        assertThrows(Exception.class, () -> chefService.registerChefUser(registerChefDTOWithDifferentPasswords));
    }
    @Test
    public void testRegisterUserWithChefAndSamePasswords() throws Exception {
        RegisterChefDTO registerChefDTOWithSamePasswords = new RegisterChefDTO(userDetailDTO, chefDetailDTO);
        registerChefDTOWithSamePasswords.getUserDetailDTO().setPassword("password");
        registerChefDTOWithSamePasswords.getUserDetailDTO().setPassword("password");
        doReturn(this.user).when(userRepository).save(this.user);
        doReturn(this.chef).when(chefRepository).save(this.chef);
        assertEquals("You have been successfully registered as chef.",chefService.registerChefUser(registerChefDTOWithSamePasswords));
    }

}
