package com.cooks.demo.service;

import com.cooks.demo.model.Slot;
import com.cooks.demo.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SlotService {
    @Autowired
    SlotRepository slotRepository;

    /**
     * creates slots for the next month to facilitate bookings of COOK
     * @throws ParseException
     */
    public String createSlots() throws ParseException{
        List<Slot> slots = new ArrayList<Slot>();
        java.time.LocalDate date = LocalDate.now().withDayOfMonth(1);
        java.time.LocalTime time = LocalTime.MIDNIGHT;
        java.time.LocalDateTime date2 = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0);
        for(int j =0; j<6;j++) {
            date = date.plusDays(1);
            time = LocalTime.MIDNIGHT;
            for (int i = 0; i < 48; i++) {
                slots.add(Slot.builder().date(date).time(time).build());
                time = time.plusMinutes(30);
            }
        }
       slotRepository.saveAll(slots);
        return "slots created successfully";
    }
  }
