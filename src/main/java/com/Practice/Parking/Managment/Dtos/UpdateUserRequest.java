package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateUserRequest {

    private String userName;

    private Role role;

    private String email;

    @NonNull
    private String company;
}
