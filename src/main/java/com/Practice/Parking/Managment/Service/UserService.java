package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CreateUserRequest;
import com.Practice.Parking.Managment.Dtos.UserResponse;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import com.Practice.Parking.Managment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;


    public long createUser(CreateUserRequest createUser) {
        Company company = companyRepository.findByCompanyName(createUser.getCompany())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        User user= User.builder().userName(createUser.getUserName()).role(createUser.getRole()).email(createUser.getEmail()).companyId(company.getId()).build();
        return this.userRepository.save(user).getId();
    }
    public UserResponse getUser(long Id){
        User user=this.userRepository.getById(Id);
        Company company=companyRepository.findById(user.getCompanyId());
        return UserResponse.builder().userName(user.getUserName()).role(user.getRole()).email(user.getEmail()).company(company.getCompanyName()).build();
    }
}
