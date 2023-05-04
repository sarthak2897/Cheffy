package com.cooks.demo.service;

import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Order;
import com.cooks.demo.repository.BookedSlotRepository;
import com.cooks.demo.repository.ChefRepository;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.repository.SlotRepository;
import com.cooks.demo.util.DTOS.AvailableAllocation;
import com.cooks.demo.util.DTOS.RequestOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookSlotService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    ChefRepository chefRepository;

    @Autowired
    ChefService chefService;

    @Autowired
    OrderService orderService;

    @Autowired
    MailService mailService;

    @Autowired
    BookedSlotRepository bookedSlotRepository;

    /**
     * accepts the order from customer and allocates chef if available based on criteria
     * @param requestOrderDTO
     * @return
     * @throws Exception
     */
    public Integer acceptOrderAndAllocateChef(RequestOrderDTO requestOrderDTO) throws Exception {
        List<String> statuses = new ArrayList<>();
        statuses.add("Completed");
        statuses.add("Allocated");
        Order order = orderService.postOrders(requestOrderDTO);

        List<Integer> eligibleChefs = getChefsWhoMeetRequestedCriteria(requestOrderDTO);
        if (eligibleChefs.size() == 0) {
            order.setStatus("Failed");
            orderRepository.save(order);
            throw new Exception("No Chefs are available with selected criteria");
        } else {
            List<Integer> orders = (List<Integer>) orderRepository.getAllNonCompletedOrders(requestOrderDTO.getDate(), statuses);
            Integer finalizedChefId = getAvailableChefsFromEligibleChefsOnGivenDate(eligibleChefs, requestOrderDTO.getDate(), orders);
            if (finalizedChefId != null) {
                Optional<Chef> allocatedChef = chefRepository.findById(finalizedChefId);
                if (allocatedChef.isPresent()) {
                    order.setStatus("Allocated");
                    order.setChef(allocatedChef.get());
                    orderRepository.save(order);
                    String message = "your order has been registered with us and we have chefs available to serve you. please make 20% of payment to receive details of chef! please feel free to reach out to our customer care if you need any help ";
                    mailService.sendEmail(order.getCustomer().getUser().getEmail(), "Chef Available to Serve you", message);
                    return order.getId();
                }
            } else {
                order.setStatus("Failed");
                orderRepository.save(order);
                throw new Exception("All our eligible chefs are busy on given day");
            }
        }
        order.setStatus("Failed");
        orderRepository.save(order);
        throw new Exception("Allocation failed");
    }

    /**
     * gives list of available chefs based on date and requested criteria for chef
     * @param eligibleChefs
     * @param date
     * @param orders
     * @return
     * @throws Exception
     */
    public Integer getAvailableChefsFromEligibleChefsOnGivenDate(List<Integer> eligibleChefs, LocalDate date, List<Integer> orders) throws Exception {
        Integer allocatedChef = null;
        for (Integer eligibleChefId : eligibleChefs) {
            boolean allocatable = true;
            for (Integer bookedChef : orders) {
                if (bookedChef == eligibleChefId) {
                    allocatable = false;
                }
            }
            if (allocatable == true) {
                allocatedChef = eligibleChefId;
                break;
            }
        }

        return allocatedChef;
    }

    /** This Function Handles COOK BOOKING and is currently unused
     * This function accepts the customer order and stores in the database
     *
     * @param requestOrderDTO
     * @return
     * @throws Exception DO NOT DELETE THIS METHOD IT CAN BE USED FOR HANDLING COOKS SCENARIO
     */
    public List<AvailableAllocation> getAvailableChefsForDifferentSlots(RequestOrderDTO requestOrderDTO) throws Exception {
        Order order = orderService.postOrders(requestOrderDTO);
        List<Integer> chefs = getChefsWhoMeetRequestedCriteria(requestOrderDTO);
        List<Integer> alreadyBookedChefsForSlot = new ArrayList<Integer>();
        List<Integer> slotIds = getSlotsRequestedByCustomerBasedOnDateAndTime(requestOrderDTO);
        System.out.println("SlotIDs =" + slotIds);
        List<AvailableAllocation> chefsWhoCanBeAllocatedForDifferentSlots = new ArrayList<AvailableAllocation>();

        for (Integer slot : slotIds) {
            alreadyBookedChefsForSlot = getBookedChefsForSlot(slot);
            AvailableAllocation allocation = new AvailableAllocation();
            allocation.setSlotId(slot);

            if (alreadyBookedChefsForSlot.size() == 0) {
                allocation.setAvailableChefs(chefs);
            } else {
                allocation.setAvailableChefs((List<Integer>) (chefRepository.getAvailableChefIds(chefs, alreadyBookedChefsForSlot)));
            }
            chefsWhoCanBeAllocatedForDifferentSlots.add(allocation);
        }
        return chefsWhoCanBeAllocatedForDifferentSlots;
    }

    /**
     * finds list of Eligible chefs according to given criteria
     * @param requestOrderDTO
     * @return
     */
    public List<Integer> getChefsWhoMeetRequestedCriteria(RequestOrderDTO requestOrderDTO) {
        List<Integer> chefs;
        List<Chef> chefs2 = chefService.getAllChefs();
        chefs = chefs2.stream().filter(
                x -> x.getTier().equals(requestOrderDTO.getChefTier())
                        && x.getUser().getGender().equals(requestOrderDTO.getGenderPreference())
        ).map(x -> x.getId())
                .collect(Collectors.toList());
        return chefs;
    }

    /**
     * identifies the chefs who have been booked for a particular slot
     * @param slot
     * @return
     */
    public List<Integer> getBookedChefsForSlot(Integer slot) {
        return (List<Integer>) bookedSlotRepository.getBookedChefForSlot(slot);
    }

    /**
     * gets the whole list of slots based on time and Date DESIGNED FOR COOK BOOKING and currently in-used
     * @param requestOrderDTO
     * @return
     * @throws Exception
     */
    public List<Integer> getSlotsRequestedByCustomerBasedOnDateAndTime(RequestOrderDTO requestOrderDTO) throws Exception {
        Integer numberOfDays = getNumberOfDaysBasedOnSelectMealPlan(requestOrderDTO);
        LocalDate date = requestOrderDTO.getDate();
        List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < numberOfDays; i++) {
            List<Integer> integers = (List<Integer>) (slotRepository.selectSlotIdByTimeAndDate(date, requestOrderDTO.getTime()));
            slots.addAll(slotRepository.selectSlotIdByTimeAndDate(date, requestOrderDTO.getTime()));
            date = date.plusDays(1);
        }
        return slots;
    }

    /**
     * identifies number of days service need to be rendered based on selected plan
     * @param requestOrderDTO
     * @return
     */
    public Integer getNumberOfDaysBasedOnSelectMealPlan(RequestOrderDTO requestOrderDTO) {
        if (requestOrderDTO.getOccasion().equals("Occasion"))
            return 1;
        else if (requestOrderDTO.getOccasion().equals("FullWeek"))
            return 7;
        else return 30;

    }
}
