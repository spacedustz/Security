package com.security.auth;

import com.security.user.User;
import com.security.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/* 유저의 정보를 Load하는 핵심 인터페이스 */

/**
 * Custom한 UserDetailsService를 구현하기 위해서 UserDetailsService 인터페이스를 구현해야 함
 * DB에서 User를 조회하고, 조회한 User의 권한(Role)을 생성하기 위해 CustomAuthority DI 받음
 */
@Component
public class CustomDetails implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomAuthority authority;

    public CustomDetails(UserRepository userRepository, CustomAuthority authority) {
        this.userRepository = userRepository;
        this.authority = authority;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByName(username);
        User user = optUser.orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        // DB에서 조회한 User의 객체를 리턴하면 Spring Security가 이 정보를 이용해 인증 절차를 수행함
        // 즉, 인증 정보만 Spring Security에 넘기고, 인증 처리는 Spring Security가 대신 수행함
        return new InnerDetails(user);
    }

    // User Details 생성
    private final class InnerDetails extends User implements UserDetails {
        InnerDetails(User user) {
            setName(user.getName());
            setPassword(user.getPassword());
            setAge(user.getAge());
            setRole(user.getRole());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authority.createAuthorities(this.getRole());
        }

        @Override
        public String getUsername() {
            return getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
