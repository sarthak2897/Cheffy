package com.cooks.demo.service;

import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Order;
import com.cooks.demo.model.User;
import com.cooks.demo.repository.BookedSlotRepository;
import com.cooks.demo.repository.ChefRepository;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.repository.SlotRepository;
import com.cooks.demo.util.DTOS.RequestOrderDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookedSlotServiceTest {
      ObjectService objectService = new ObjectService();

      User user1 = objectService.getUserObject();
      User user2 = objectService.getUserObject();
      Chef chef1 = objectService.getChefObject();
      Chef chef2 = objectService.getChefObject();
      Order order = objectService.getOrder();
      RequestOrderDTO requestOrderDTO = objectService.getRequestOrderDTO();

    @Autowired
    BookSlotService bookedSlotService;

    @MockBean
    ChefRepository chefRepository;

    @MockBean
    SlotRepository slotRepository;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    BookedSlotRepository bookedSlotRepository;

    @Test
    public void TestGetNumberOfDaysBasedOnSelectMealPlanForOccasion() {
        assertEquals(1, bookedSlotService.getNumberOfDaysBasedOnSelectMealPlan(requestOrderDTO));
    }

    @Test
    public void TestGetNumberOfDaysBasedOnSelectMealPlanForWeek() {
        requestOrderDTO.setOccasion("FullWeek");
        assertEquals(7, bookedSlotService.getNumberOfDaysBasedOnSelectMealPlan(requestOrderDTO));
    }

    @Test
    public void TestGetNumberOfDaysBasedOnSelectMealPlanForMonth() {
        requestOrderDTO.setOccasion("Month");
        assertEquals(30, bookedSlotService.getNumberOfDaysBasedOnSelectMealPlan(requestOrderDTO));
    }

    @Test
    public void TestGetChefsWhoMeetRequestedCriteria(){
        List<Chef> chefs = new ArrayList<Chef>();
        chefs.add(chef1);
        chefs.add(chef2);
        doReturn(chefs).when(chefRepository).findAll();
        assertEquals(2, bookedSlotService.getChefsWhoMeetRequestedCriteria(requestOrderDTO).size());
    }

    @Test
    public void TestGetChefsWhoMeetRequestedCriteriaWithFemale(){
        requestOrderDTO.setGenderPreference("Female");
        List<Chef> chefs = new ArrayList<Chef>();
        chefs.add(chef1);
        chefs.add(chef2);
        doReturn(chefs).when(chefRepository).findAll();
        assertEquals(0, bookedSlotService.getChefsWhoMeetRequestedCriteria(requestOrderDTO).size());
    }
    @Test
    public void TestGetChefsWhoMeetRequestedCriteriaWithTire2(){
        requestOrderDTO.setChefTier(2);
        List<Chef> chefs = new ArrayList<Chef>();
        chefs.add(chef1);
        chefs.add(chef2);
        doReturn(chefs).when(chefRepository).findAll();
        assertEquals(0, bookedSlotService.getChefsWhoMeetRequestedCriteria(requestOrderDTO).size());


    }

    @Test
    public void TestGetSlotsRequestedByCustomerBasedOnDateAndTime() throws Exception{
        Collection<Integer> slots = new ArrayList<>();
        slots.add(2);
        System.out.println(slots);
        doReturn(slots).when(slotRepository).selectSlotIdByTimeAndDate(requestOrderDTO.getDate(),requestOrderDTO.getTime());
        assertEquals(1,bookedSlotService.getSlotsRequestedByCustomerBasedOnDateAndTime(requestOrderDTO).size());
    }

    @Test
    public void TestAcceptOrderAndAllocateChef(){
        when(orderRepository.save(this.order)).thenReturn(this.order);
        List<Chef> chefs = new ArrayList<Chef>();
        chefs.add(chef1);
        chefs.add(chef2);
        doReturn(chefs).when(chefRepository).findAll();
        requestOrderDTO.setGenderPreference("Female");
        assertThrows(Exception.class, () -> bookedSlotService.acceptOrderAndAllocateChef(requestOrderDTO));
    }

    @Test
    public  void TestAcceptOrderAndAllocateChefElse(){
        when(orderRepository.save(this.order)).thenReturn(this.order);
        List<Chef> chefs = new ArrayList<Chef>();
        chefs.add(chef1);
        chefs.add(chef2);
        doReturn(chefs).when(chefRepository).findAll();

    }

//    @Test
//    public void  TestGetBookedChefsForSlot(){
//        List<Integer> bookedChefs = new ArrayList<>();
//        bookedChefs.add(1);
//        bookedChefs.add(2);
//        List<String> statuses = new ArrayList<String>();
//        statuses.add("Completed");
//        statuses.add("Allocated");
//        doReturn(bookedChefs).when(bookedSlotRepository).getBookedChefForSlot(1);
//        doReturn(bookedChefs).when(orderRepository.getAllNonCompletedOrders(requestOrderDTO.getDate(),statuses));
//        when(chefRepository.save(chef1)).thenReturn(chef1);
//        assertThrows(Exception.class, () -> bookedSlotService.acceptOrderAndAllocateChef(requestOrderDTO));
//
//    }
}

