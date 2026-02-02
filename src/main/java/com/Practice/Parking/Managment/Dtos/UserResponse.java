package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.Role;
import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {

    private String userName;

    private Role role;

    private String email;

    @NonNull
    private String company;


}
