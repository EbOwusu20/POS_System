package com.pos.pos_system.config;

import com.pos.pos_system.model.Role;
import com.pos.pos_system.model.User;
import com.pos.pos_system.repository.UserRepository;
import com.pos.pos_system.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    private final PasswordEncoder passwordEncoder;

    /**
     * When true (default), admin/manager/cashier are created or password+role reset on every startup
     * so local/testing logins always work. Set false in production.
     */
    @Value("${app.ensure-demo-users:true}")
    private boolean ensureDemoUsers;

    public DataSeeder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner seedDefaultUsers(UserRepository userRepository, UserService userService) {
        return args -> {
            if (ensureDemoUsers) {
                upsertDefaultUser(userRepository, userService, "admin", "admin123", Role.ADMIN);
                upsertDefaultUser(userRepository, userService, "manager", "manager123", Role.MANAGER);
                upsertDefaultUser(userRepository, userService, "cashier", "cashier123", Role.CASHIER);
                return;
            }
            if (userRepository.count() == 0) {
                userService.register(new User("admin", "admin123", Role.ADMIN));
                userService.register(new User("manager", "manager123", Role.MANAGER));
                userService.register(new User("cashier", "cashier123", Role.CASHIER));
            }
        };
    }

    /**
     * Creates the user if missing; otherwise replaces password (and role) with seeded values.
     */
    private void upsertDefaultUser(
            UserRepository userRepository,
            UserService userService,
            String username,
            String rawPassword,
            Role role
    ) {
        userRepository.findByUsername(username).ifPresentOrElse(
                u -> {
                    u.setPassword(passwordEncoder.encode(rawPassword));
                    u.setRole(role);
                    userRepository.save(u);
                },
                () -> userService.register(new User(username, rawPassword, role))
        );
    }
}

