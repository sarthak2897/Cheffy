package com.cooks.demo.util.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    private String email;
    private String password;
    private String verifyPassword;
}
