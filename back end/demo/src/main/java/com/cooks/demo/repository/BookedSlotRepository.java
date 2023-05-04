package com.cooks.demo.repository;

import com.cooks.demo.model.BookedSlots;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface BookedSlotRepository extends CrudRepository<BookedSlots, Integer> {
    @Query(value = "SELECT * FROM booked_slots where booked_slots.chef_id =:chefId", nativeQuery = true)
    Collection<BookedSlots> selectSlots(@Param("chefId") Integer chefId);

    @Query(value = "select chef_id from booked_slots where slot_id = :slotId", nativeQuery = true)
    Collection<Integer> getBookedChefForSlot(@Param("slotId") Integer slotId);
}
