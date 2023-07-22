package com.security.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.name = :name")
    Optional<User> findByName(@Param("name") String name);

    @Query(value = "select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}
