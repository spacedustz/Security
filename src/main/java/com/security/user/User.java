package com.security.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter private String name;
    @Setter private int age;
    @Setter private Role role = Role.ROLE_USER;
    @Setter private String password;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt;
    private Boolean deletedAt = false;

    private User(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public static User createOf(String name, String password, int age) {
        return new User(name, password, age);
    }

    public void updateUser(String name, int age) {
        this.name = name;
        this.age = age;
        this.modifiedAt = LocalDateTime.now();
    }

    public void deleteUser() {
        this.deletedAt = true;
    }
}
