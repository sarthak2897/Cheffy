package com.cooks.demo.util.DTOS;

import com.cooks.demo.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestOrderDTO {

    private String mealType;
    //    @NonNull
    private String foodPreference;
    //    @NonNull
    private String genderPreference;
    //    @NonNull
    private String cuisine;
    //    @NonNull
    private String occasion;
    //    @NonNull
    private int noOfPeople;
    //    @NonNull
    private String typeOfHob;
    //    @NonNull
    private int noOfHobs;
    //    @NonNull
    private boolean isOven;
    //    @NonNull
    private int chefTier;
    //    @NonNull
    private boolean isIngredients;

    private int customerID;
    private String status;
    private Long amount;
//    @NotNull
//    private int chefID;
//    @Embedded
//    private Address address;

    private java.time.LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    private java.time.LocalTime time;

}
