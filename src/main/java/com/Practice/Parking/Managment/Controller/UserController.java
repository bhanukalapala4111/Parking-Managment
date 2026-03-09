package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CreateUserRequest;
import com.Practice.Parking.Managment.Dtos.UpdateUserRequest;
import com.Practice.Parking.Managment.Dtos.GetUserResponse;
import com.Practice.Parking.Managment.Model.SlotHistory;
import com.Practice.Parking.Managment.Repository.SlotHistoryRepository;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import com.Practice.Parking.Managment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SlotHistoryRepository slotHistoryRepository;

    @PostMapping("/create")
    public long CreateUser(@RequestBody CreateUserRequest createUser, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY_ADMIN"))) {
            Long authorizedCompanyId = (Long) authentication.getDetails();
            var company = companyRepository.findByCompanyName(createUser.getCompany())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            if (authorizedCompanyId == null || !authorizedCompanyId.equals(company.getId())) {
                throw new RuntimeException("Unauthorized: You can only create users for your own company.");
            }
        }
        return this.userService.createUser(createUser);
    }

    @GetMapping("/get/{Id}")
    public GetUserResponse GetUser(@PathVariable("Id") long Id) {
        return this.userService.getUser(Id);
    }

    @PatchMapping("/update/{Id}")
    public GetUserResponse UpdateUser(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable("Id") long Id,
            Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY_ADMIN"))) {
            Long authorizedCompanyId = (Long) authentication.getDetails();
            var user = userService.getUser(Id).getUser(); // Assuming getUser returns a wrapper
            if (authorizedCompanyId == null || !authorizedCompanyId.equals(user.getCompanyId())) {
                throw new RuntimeException("Unauthorized: You can only update users for your own company.");
            }
            if (updateUserRequest.getCompany() != null) {
                var newCompany = companyRepository.findByCompanyName(updateUserRequest.getCompany())
                        .orElseThrow(() -> new RuntimeException("Company not found"));
                if (!authorizedCompanyId.equals(newCompany.getId())) {
                    throw new RuntimeException("Unauthorized: You cannot move a user to a different company.");
                }
            }
        }
        return this.userService.updateUser(updateUserRequest, Id);
    }

    @DeleteMapping("/delete/{Id}")
    public void deleteUser(@PathVariable("Id") long Id, Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY_ADMIN"))) {
            Long authorizedCompanyId = (Long) authentication.getDetails();
            var user = userService.getUser(Id).getUser();
            if (authorizedCompanyId == null || !authorizedCompanyId.equals(user.getCompanyId())) {
                throw new RuntimeException("Unauthorized: You can only delete users from your own company.");
            }
        }
        this.userService.deleteUser(Id);
    }

    @GetMapping("/all")
    public List<GetUserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/company/{companyId}")
    public List<GetUserResponse> getCompanyUsers(@PathVariable("companyId") long companyId,
            Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY_ADMIN"))) {
            Long authorizedCompanyId = (Long) authentication.getDetails();
            if (authorizedCompanyId == null || !authorizedCompanyId.equals(companyId)) {
                throw new RuntimeException("Unauthorized: You can only view users for your own company.");
            }
        }
        return this.userService.getUsersByCompany(companyId);
    }

    @GetMapping("/{userId}/history")
    public List<SlotHistory> getHistory(@PathVariable("userId") Long userId) {
        return slotHistoryRepository.findTop10ByUserIdOrderByBookingDateDesc(userId);
    }

}
