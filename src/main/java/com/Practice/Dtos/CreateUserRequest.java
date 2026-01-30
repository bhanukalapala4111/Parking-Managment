package com.Practice.Dtos;

import com.Practice.Model.Role;
import com.Practice.Model.User;
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

    @NonNull
    private String company;
}
