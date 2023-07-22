package com.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/* User의 권한을 Mapping, 생성하는 Custom Authority 클래스 */
@Component
public class CustomAuthority {

    // 관리자용 권한 리스트
    private final List<String> ADMIN_ROLE = List.of("ADMIN", "USER");

    // 일반 유저용 권한 리스트
    private final List<String> USER_ROLES = List.of("USER");

    // DP에 저장된 Role을 기반으로 권한 정보 생성
    // SimpleGrantedAuthority에 Role을 매핑할때 "ROLE_" 을 안붙이면 매핑이 안됨
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public List<String> createRoles(String email) {
        String adminEmail = "admin@admin.com";
        if (email.equals(adminEmail)) return ADMIN_ROLE;
        return USER_ROLES;
    }
}
