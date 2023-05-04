package com.cooks.demo.util.DTOS;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableAllocation {
    Integer SlotId;
    List<Integer> availableChefs;
}
