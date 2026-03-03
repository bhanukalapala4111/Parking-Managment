package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CreateUserRequest;
import com.Practice.Parking.Managment.Dtos.UpdateUserRequest;
import com.Practice.Parking.Managment.Dtos.GetUserResponse;
import com.Practice.Parking.Managment.Model.SlotHistory;
import com.Practice.Parking.Managment.Repository.SlotHistoryRepository;
import com.Practice.Parking.Managment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SlotHistoryRepository slotHistoryRepository;

    @PostMapping("/create")
    public long CreateUser(@RequestBody CreateUserRequest createUser) {
        return this.userService.createUser(createUser);
    }

    @GetMapping("/get/{Id}")
    public GetUserResponse GetUser(@PathVariable("Id") long Id) {
        return this.userService.getUser(Id);
    }

    @PatchMapping("/update/{Id}")
    public GetUserResponse UpdateUser(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable("Id") long Id) {
        return this.userService.updateUser(updateUserRequest, Id);
    }

    @GetMapping("/all")
    public List<GetUserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userId}/history")
    public List<SlotHistory> getHistory(@PathVariable("userId") Long userId) {
        return slotHistoryRepository.findTop10ByUserIdOrderByBookingDateDesc(userId);
    }

}
