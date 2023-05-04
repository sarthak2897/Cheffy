package com.cooks.demo.repository;
import com.cooks.demo.model.Chef;
import com.cooks.demo.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ChefRepository extends CrudRepository<Chef, Integer> {

    @Query(value = "select id from chef where id in(:chefs) and id not in(:bookedChefs)", nativeQuery = true)
    public Collection<Integer> getAvailableChefIds(@Param("chefs") List<Integer> chefs, @Param("bookedChefs")List<Integer> bookedChefs);

    @Query(value = "select * from chef where user_id=:userId",nativeQuery = true)
    public Chef getChefByUserId(@Param("userId") Integer userId);

}
