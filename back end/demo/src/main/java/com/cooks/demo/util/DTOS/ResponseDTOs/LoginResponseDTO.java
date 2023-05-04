package com.cooks.demo.util.DTOS.ResponseDTOs;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String username;
    private String email;
    private Integer accountId;
    private String role;
}
