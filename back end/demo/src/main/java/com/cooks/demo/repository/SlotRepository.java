package com.cooks.demo.repository;
import com.cooks.demo.model.Slot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface SlotRepository extends CrudRepository<Slot,Integer> {

    @Query(value = "SELECT id FROM slots where date = :date and time = :time", nativeQuery = true)
    public Collection<Integer> selectSlotIdByTimeAndDate(@Param("date") LocalDate date, @Param("time")LocalTime time);
}
