package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Model.ParkingSlot;
import org.springframework.context.annotation.Lazy;

import com.Practice.Parking.Managment.Dtos.CreateUserRequest;
import com.Practice.Parking.Managment.Dtos.GetUserResponse;
import com.Practice.Parking.Managment.Dtos.UpdateUserRequest;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.Role;
import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import com.Practice.Parking.Managment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    ParkingSlotService parkingSlotService;

    public long createUser(CreateUserRequest createUser) {
        if (createUser.getRole() == Role.ADMIN) {
            throw new RuntimeException("ADMIN users cannot be created through the API.");
        }

        Company company = companyRepository.findByCompanyName(createUser.getCompany())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        User user = User.builder().userName(createUser.getUserName()).role(createUser.getRole())
                .password(passwordEncoder.encode(createUser.getPassword())).email(createUser.getEmail())
                .companyId(company.getId()).build();
        return this.userRepository.save(user).getId();
    }

    public GetUserResponse getUser(long Id) {
        User user = this.userRepository.getById(Id);
        return GetUserResponse.builder().user(user).build();
    }

    public GetUserResponse updateUser(UpdateUserRequest request, long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

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
            if (request.getRole() == Role.ADMIN) {
                throw new RuntimeException("Cannot assign ADMIN role through the API.");
            }
            existingUser.setRole(request.getRole());
        }

        // company is mandatory
        if (request.getCompany() != null &&
                !Objects.equals(companyRepository.findById(existingUser.getCompanyId()).getCompanyName(),
                        request.getCompany())) {
            Company company = companyRepository.findByCompanyName(request.getCompany())
                    .orElseThrow(() -> new RuntimeException("Company not found by Name"));

            existingUser.setCompanyId(company.getId());
        }

        return existingUser;
    }

    public List<GetUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() != Role.ADMIN)
                .map(user -> GetUserResponse.builder().user(user).build())
                .collect(Collectors.toList());
    }

    public List<GetUserResponse> getUsersByCompany(long companyId) {
        return userRepository.findByCompanyId(companyId).stream()
                .map(user -> GetUserResponse.builder().user(user).build())
                .collect(Collectors.toList());
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        // Release all slots booked by user before deletion
        List<ParkingSlot> bookedSlots = parkingSlotService.getUserBookings(id);
        if (bookedSlots != null && !bookedSlots.isEmpty()) {
            for (ParkingSlot slot : bookedSlots) {
                parkingSlotService.releaseSlot(slot.getId());
            }
        }

        userRepository.deleteById(id);
    }
}
