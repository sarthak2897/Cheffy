package com.cooks.demo.util.DTOS;

import com.cooks.demo.model.Address;
import com.cooks.demo.model.Chef;
import lombok.*;
import org.hibernate.usertype.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCustomerDTO {
    private UserDetailDTO userDetailDTO;
    private AddressDTO address;
}
