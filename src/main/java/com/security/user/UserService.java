package com.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public User create(UserDto.Post data) {
        List<GrantedAuthority> auth = createAuthorities(Role.USER.name());

        String encrypt = encoder.encode(data.getPassword());

        User user = User.createOf(data.getName(), encrypt, data.getAge());

        UserDetails details = (UserDetails) user;

        manager.createUser(details);

        userRepository.save(user);

        return user;
    }

    private List<GrantedAuthority> createAuthorities(String... roles) {
        return Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
