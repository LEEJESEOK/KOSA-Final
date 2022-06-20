package com.hyundai.kosafinal.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleSetDTO {
    private String role_set;
    private String user_email;
}
