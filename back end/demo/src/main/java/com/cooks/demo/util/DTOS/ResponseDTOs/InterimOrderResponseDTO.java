package com.cooks.demo.util.DTOS.ResponseDTOs;

import com.cooks.demo.util.DTOS.AddressDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterimOrderResponseDTO {
    private Integer orderId;
    private String cuisine;
    private String foodPreference;
    private String mealType;
    private String occasion;
    private Long totalAmount;
    private Long amountToBePaid;
    private AddressDTO addressDTO;
    private LocalDate orderDate;
    private Integer noOfPeople;
    private Integer chefTier;
    private String email;
}
