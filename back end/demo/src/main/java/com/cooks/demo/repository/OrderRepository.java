package com.cooks.demo.repository;
import com.cooks.demo.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query(value = "SELECT chef_id FROM customer_order where date_of_supply =:dateOfSupply and status in(:statuses)", nativeQuery = true)
    Collection<Integer> getAllNonCompletedOrders(@Param("dateOfSupply") LocalDate dateOfSupply, @Param("statuses") List<String> statuses);

    @Query(value = "SELECT * FROM customer_order where customer_id =:customerId", nativeQuery = true)
    List<Order> getAllOrdersOfCustomer(@Param("customerId") Integer customerId);

    @Query(value = "SELECT * FROM customer_order where chef_id =:chefId", nativeQuery = true)
    List<Order> getAllOrdersOfChef(@Param("chefId") Integer ChefId);

}
