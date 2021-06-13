package com.epam.esm.model.dto;

import lombok.Data;

@Data
public class AuthenticationDto {
    private String name;
    private String password;
}
