package com.pos.pos_system.controller;

import com.pos.pos_system.dto.UserCreateRequestDTO;
import com.pos.pos_system.model.User;
import com.pos.pos_system.repository.UserRepository;
import com.pos.pos_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserCreateRequestDTO req) {
        User user = new User(req.getUsername(), req.getPassword(), req.getRole());
        return userService.register(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}

