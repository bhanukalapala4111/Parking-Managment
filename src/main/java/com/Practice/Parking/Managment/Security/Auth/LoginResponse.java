package com.Practice.Parking.Managment.Security.Auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.Practice.Parking.Managment.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String token;

    @JsonProperty("userName")
    private String userName;

    private Role role;

    public String getUserName() {
        return userName;
    }
}
