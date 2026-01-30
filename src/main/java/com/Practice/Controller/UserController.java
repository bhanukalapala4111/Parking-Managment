package com.Practice.Controller;

import com.Practice.Dtos.CreateUserRequest;
import com.Practice.Dtos.UserResponse;
import com.Practice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    long CreateUser(@RequestBody CreateUserRequest createUser){
          return this.userService.createUser(createUser);
    }
    @GetMapping("/get/{Id}")
    UserResponse GetUser(@PathVariable long Id){
        return this.userService.getUser(Id);
    }
    @PatchMapping("/updateUser/{Id}")
    void UpdateUser(@RequestBody CreateUserRequest createUserRequest, @PathVariable long Id){

    }
    }

