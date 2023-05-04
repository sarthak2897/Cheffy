package com.cooks.demo.service;

import com.cooks.demo.model.Customer;
import com.cooks.demo.model.Order;
import com.cooks.demo.repository.CustomerRepository;
import com.cooks.demo.repository.OrderRepository;
import com.cooks.demo.util.DTOS.RequestOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.WebSocketHandshakeException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    /**
     * gets order by Id
     * @param orderId
     * @return
     * @throws Exception
     */
    public Order getGetOderById(Integer orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        else {
            throw new Exception("order with this Id not found :" + orderId);
        }
    }

    /**
     * saves order to the database
     * @param requestOrderDTO
     * @return
     * @throws Exception
     */
    public Order postOrders(RequestOrderDTO requestOrderDTO) throws Exception{
            Optional<Customer> customer = customerRepository.findById(requestOrderDTO.getCustomerID());
            if(customer.isPresent()) {
                Order order = parseRequestToOrder(requestOrderDTO);
                Customer presentCustomer = customer.get();
                order.setCustomer(presentCustomer);
                orderRepository.save(order);
                return  order;
            }
            else {
                throw new Exception("Customer not found");
            }
    }

    /**
     * converts requested DTO object to the Order Object
     * @param requestOrderDTO
     * @return
     */
    public Order parseRequestToOrder(RequestOrderDTO requestOrderDTO){
        Order order = new Order();
        order.setMealType(requestOrderDTO.getMealType());
        order.setFoodPreference(requestOrderDTO.getFoodPreference());
        order.setGenderPreference(requestOrderDTO.getGenderPreference());
        order.setCuisine(requestOrderDTO.getCuisine());
        order.setOccasion(requestOrderDTO.getOccasion());
        order.setNoOfPeople(requestOrderDTO.getNoOfPeople());
        order.setTypeOfHob(requestOrderDTO.getTypeOfHob());
        order.setNoOfHobs(requestOrderDTO.getNoOfHobs());
        order.setOven(requestOrderDTO.isOven());
        order.setChefTier(requestOrderDTO.getChefTier());
        order.setIngredients(requestOrderDTO.isIngredients());
        order.setDateOfSupply(requestOrderDTO.getDate());
        order.setDateOfOrder(LocalDateTime.now());
        order.setStatus("Requested");
        order.setAmount(requestOrderDTO.getAmount());
        return order;
    }
}
