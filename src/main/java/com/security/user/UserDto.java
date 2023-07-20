package com.security.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class UserDto {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Post {
        private String name;
        private String password;
        private int age;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private Long id;
        private String name;
        private int age;
        private List<String> role;
        private LocalDateTime createdAt;

        public static UserDto.Response fromEntity(User user) {
            return new UserDto.Response(
                    user.getId(),
                    user.getName(),
                    user.getAge(),
                    user.getRole(),
                    user.getCreatedAt()
            );
        }
    }
}
