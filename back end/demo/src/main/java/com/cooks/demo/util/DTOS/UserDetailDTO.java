package com.cooks.demo.util.DTOS;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private String name;
    @Email(message = "this is not a valid email address")
    private String email;
    private String gender;
    private Long phoneNumber;
    private String userType;
    private String password;
    private String verifyPassword;
}
