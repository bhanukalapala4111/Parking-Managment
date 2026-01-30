package com.Practice.Service;

import com.Practice.Dtos.CreateUserRequest;
import com.Practice.Dtos.UserResponse;
import com.Practice.Model.Company;
import com.Practice.Model.User;
import com.Practice.Repository.CompanyRepository;
import com.Practice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserResponse userResponse;

    public long createUser(CreateUserRequest createUser) {
        Company company = companyRepository.findByCompanyName(createUser.getCompany())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        User user= User.builder().userName(createUser.getUserName()).role(createUser.getRole()).email(createUser.getEmail()).companyId(company.getId()).build();
        return this.userRepository.save(user).getId();
    }
    public UserResponse getUser(long Id){
        User user=this.userRepository.getById(Id);

        return this.userResponse.toUser(user, companyRepository);
    }
}
