package com.cooks.demo.util.DTOS;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginDTO {

    @NonNull
    private String username;
    @NonNull
    private String password;
}
