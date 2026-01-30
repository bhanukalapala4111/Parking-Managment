package com.Practice.Dtos;

import com.Practice.Model.Company;
import com.Practice.Model.Role;
import com.Practice.Model.User;
import com.Practice.Repository.CompanyRepository;
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

    public UserResponse toUser(User user , CompanyRepository companyRepository){
        Company company=companyRepository.findById(user.getCompanyId());
        return UserResponse.builder().userName(user.getUserName()).role(user.getRole()).email(user.getEmail()).company(company.getCompanyName()).build();
    }
}
