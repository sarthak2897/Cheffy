package com.cooks.demo.util.DTOS;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddressDTO {

    @NonNull
    private String addressLine1;

    private String addressLine2;

    @NonNull
    private String city;

    @NonNull
    private String state;

    @NonNull
    private Long pinCode;

    @NonNull
    private String latitude;

    @NonNull
    private String longitude;
}