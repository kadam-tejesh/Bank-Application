package com.example.BankApplication.DTO;

import lombok.Data;

@Data

public class UserResponseDTO {
    private long id;
    private String name;
    private String email;
    private String role;
    public UserResponseDTO(){

    }
}
