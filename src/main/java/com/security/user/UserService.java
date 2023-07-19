package com.security.user;

import com.security.auth.CustomAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final CustomAuthority authority;

    public User create(UserDto.Post data) {

        // Password Encoding
        String encrypt = encoder.encode(data.getPassword());

        // Create User
        User user = User.createOf(data.getName(), encrypt, data.getAge());

        // Role 생성
        user.setRole(authority.createRoles(data.getName()));

        userRepository.save(user);

        return user;
    }
}
