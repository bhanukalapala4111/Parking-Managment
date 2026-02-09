package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CreateUserRequest;
import com.Practice.Parking.Managment.Dtos.GetUserResponse;
import com.Practice.Parking.Managment.Dtos.UpdateUserRequest;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import com.Practice.Parking.Managment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public long createUser(CreateUserRequest createUser) {
        Company company = companyRepository.findByCompanyName(createUser.getCompany())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        User user= User.builder().userName(createUser.getUserName()).role(createUser.getRole()).password(passwordEncoder.encode(createUser.getPassword())).email(createUser.getEmail()).companyId(company.getId()).build();
        return this.userRepository.save(user).getId();
    }
    public GetUserResponse getUser(long Id){
        User user=this.userRepository.getById(Id);
        return GetUserResponse.builder().user(user).build();
    }

    public GetUserResponse updateUser(UpdateUserRequest request, long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));

        User mergedUser = merge(existingUser, request);

        User savedUser = userRepository.save(mergedUser);

        return GetUserResponse.builder()
                .user(savedUser)
                .build();
    }
    private User merge(User existingUser, UpdateUserRequest request) {

        if (request.getUserName() != null &&
                !Objects.equals(existingUser.getUserName(), request.getUserName())) {
            existingUser.setUserName(request.getUserName());
        }

        if (request.getEmail() != null &&
                !Objects.equals(existingUser.getEmail(), request.getEmail())) {
            existingUser.setEmail(request.getEmail());
        }

        if (request.getRole() != null &&
                existingUser.getRole() != request.getRole()) {
            existingUser.setRole(request.getRole());
        }

        // company is mandatory
        if (request.getCompany() != null &&
                !Objects.equals(companyRepository.findById(existingUser.getCompanyId()).getCompanyName(), request.getCompany())) {
            Company company =companyRepository.findByCompanyName(request.getCompany()).orElseThrow(()->new RuntimeException("Company not found by Name"));
            existingUser.setCompanyId(company.getId());
        }

        return existingUser;
    }
}
