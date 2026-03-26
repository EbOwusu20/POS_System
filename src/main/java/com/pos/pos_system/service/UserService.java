package com.pos.pos_system.service;


import com.pos.pos_system.model.User;
import com.pos.pos_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public  UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //REGISTER USER (Encrypt Password)
    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //Login Logic (compare encrypted password)
    public User login(String username, String password ){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }


        return  user;

    }



}
