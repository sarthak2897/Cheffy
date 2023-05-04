package com.cooks.demo.util.DTOS;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChefDetailDTO {
    @NonNull
    private String qualification;

    @NonNull
    private Double experience;
}
