package com.cooks.demo.repository;

import com.cooks.demo.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    @Query(value = "select * from customer where user_id=:userId",nativeQuery = true)
    public Customer getCustomerByUserId(@Param("userId") Integer userId);

}
