package com.cooks.demo.util.Scheduler;

import com.cooks.demo.service.SlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Scheduler {

    @Autowired
    SlotService slotService;

//    @Scheduled(cron = "0 0/1 * * * ?")
//    public void updateDatabaseRecords() throws IOException, Exception {
//        log.info("Scheduler function ran" + LocalDateTime.now());
//        slotService.createSlots();
//
//    }

}
