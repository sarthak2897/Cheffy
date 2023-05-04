package com.cooks.demo.service;

import com.cooks.demo.model.Slot;
import com.cooks.demo.repository.SlotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SlotServiceTest {
   @Autowired
    SlotService slotService;

    @MockBean
    SlotRepository slotRepository;

    Slot slot1 = new Slot();
    Slot slot2 = new Slot();
    List<Slot> slotList = new ArrayList<Slot>();

    @Test
    public void testCreateSlot() throws Exception{
        when(slotRepository.saveAll(this.slotList)).thenReturn(this.slotList);
        assertEquals("slots created successfully", slotService.createSlots());
    }

}
