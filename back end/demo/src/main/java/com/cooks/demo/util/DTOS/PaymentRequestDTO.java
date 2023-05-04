package com.cooks.demo.util.DTOS;

import com.cooks.demo.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    private String token;
    private Integer amount;
    private String email;
    public enum Currency{
        INR,USD
    }
    public Currency Currency;
    public String cardDigits;
    public Integer orderId;
}
