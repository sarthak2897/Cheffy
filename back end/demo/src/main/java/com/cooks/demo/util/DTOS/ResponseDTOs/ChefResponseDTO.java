package com.cooks.demo.util.DTOS.ResponseDTOs;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefResponseDTO {
    private Integer orderId;
    private String customerName;
    private String customerEmail;
    private Long customerPhoneNumber;
    private LocalDate serviceDate;
    private String status;
    private Long amount;
    private String cuisine;
    private String mealType;
    private Integer noOfPeople;
    private String foodPreference;

}
