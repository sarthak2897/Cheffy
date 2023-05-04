package com.cooks.demo.util.DTOS.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomerResponseDTO {
    Integer orderId;
    private String name;
    private String chefEmail;
    private Long chefPhoneNumber;
    private String ChefGender;
    private Integer chefTier;
    private String orderStatus;
    private Long amount;
    private String cuisine;
    private String mealType;
    private Integer noOfPeople;
    private String foodPreference;
    private String occasion;
    private LocalDate dateOfOrder;

}

