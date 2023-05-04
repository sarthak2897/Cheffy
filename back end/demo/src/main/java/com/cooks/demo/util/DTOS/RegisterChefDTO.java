package com.cooks.demo.util.DTOS;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterChefDTO {
    private UserDetailDTO userDetailDTO;
    private ChefDetailDTO chefDetailDTO;
}
