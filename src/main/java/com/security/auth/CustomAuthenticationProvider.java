package com.security.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

/* Multi Factor Authentication을 사용하고 싶을때 직접 인증 처리를 하는 클래스 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomDetails details;
    private final PasswordEncoder encoder;

    public CustomAuthenticationProvider(CustomDetails details, PasswordEncoder encoder) {
        this.details = details;
        this.encoder = encoder;
    }

    /**
     *
     * @param authentication the authentication request object.
     * @return 인증된 Authentication
     * @throws : AuthenticationException
     * @desc 직접
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Authentication 객체를 캐스팅해 UsernamePasswordAuthenticationToken을 얻는다.
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;

        // 위에서 얻은 객체로 username을 얻고, 존재하는지 검증 후, username이 존재하면 loadUserByUsername을 이용해 DB에서 User 조회
        String username = authToken.getName();
        Optional.ofNullable(username).orElseThrow(() -> new UsernameNotFoundException("유저명을 찾을 수 없습니다."));

        // AuthenticationException이 아닌 다른 Exception 발생 시 Re-Throw 하게 만듬
        try {
            UserDetails detail = details.loadUserByUsername(username);

            // 로그인 정보에 포함된 패스워드(password)와 DB에 저장된 패스워드가 일치하는지 검증
            String password = detail.getPassword();
            verifyCredentials(authToken.getCredentials(), password);

            // 인증이 성공하면 사용자의 권한 생성
            Collection<? extends GrantedAuthority> auth = detail.getAuthorities();

            // 모든 Credential과 권한이 생성됬으면 인증된 Authentication 리턴
            return UsernamePasswordAuthenticationToken.authenticated(username, password, auth);
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    // 객체 동일성 검증
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    // Credential 검증 함수
    private void verifyCredentials(Object credentials, String password) {
        if (!encoder.matches((String) credentials, password)) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }
}
