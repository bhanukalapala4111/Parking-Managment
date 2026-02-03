package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CreateUserRequest;
import com.Practice.Parking.Managment.Dtos.UpdateUserRequest;
import com.Practice.Parking.Managment.Dtos.GetUserResponse;
import com.Practice.Parking.Managment.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public long CreateUser(@RequestBody CreateUserRequest createUser){
          return this.userService.createUser(createUser);
    }
    @GetMapping("/get/{Id}")
    public GetUserResponse GetUser(@PathVariable long Id){
        return this.userService.getUser(Id);
    }

    @PatchMapping("/update/{Id}")
    public void UpdateUser(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable long Id){
    }
    }

