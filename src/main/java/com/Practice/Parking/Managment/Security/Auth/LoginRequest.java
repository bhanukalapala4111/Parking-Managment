package com.Practice.Parking.Managment.Security.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
