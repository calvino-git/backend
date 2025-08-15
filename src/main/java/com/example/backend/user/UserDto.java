package com.example.backend.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String mobileNumber;
    private String password;
}
