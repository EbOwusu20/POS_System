
package com.pos.pos_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
    @Column(unique=true)
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {}
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getters & setters
}
