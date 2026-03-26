package com.pos.pos_system.controller;


import com.pos.pos_system.model.User;
import com.pos.pos_system.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }


    //REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    //LOGIN
    @PostMapping("/login")
    public User login(@RequestBody User user){
        return userService.login(user.getUsername(), user.getPassword());
    }






}
