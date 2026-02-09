package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Role;
import com.Practice.Parking.Managment.Model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateUserRequest {

    private String userName;

    private Role role;

    private String email;

    private String password;

    @NonNull
    private String company;
}
