package com.security.config;

import com.security.user.UserRepository;
import com.security.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class JavaConfiguration {

    /* InMemory User 등록을 위한 Service Bean 등록 */
    @Bean
    public UserService inMemoryUserService(
            UserDetailsManager manager,
            PasswordEncoder encoder,
            UserRepository repository
    ) {
        return new UserService(manager, encoder, repository);
    }
}
