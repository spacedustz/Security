package com.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.JwtTokenizer;
import com.security.user.LoginDto;
import com.security.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

/**
 * 로그인 인증 요청을 처리하는 Security Filter
 * UsernamePasswordAuthenticationFilter 상속 (Form Login 방식에서 사용
 * FormLogin 방식이 아니더라도 username/password 기반의 인증을 처리하기 위해 확장해서 구현 가능함
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    /**
     * @param authenticationManager : 로그인 인증 정보(username/password)를 전달받아 UserDetailsService와 인터랙션 한 뒤 인증 여부 판단
     * @param jwtTokenizer : JWT Token 생성
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
    }

    // 인증을 시도하는 로직
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        // DTO로 받은 Username/Password를 DTO로 역직렬화를 위해 Object Mapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // LoginDTO의 InputStream을 LoginDTO 객체로 Deserialization
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        // Username/Password 를 포함한 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword());

        // AuthenticationManager에게 Token을 전달하며 인증 처리 위임하고 성공하면 '인증된' Authentication 객체 반환
        return authenticationManager.authenticate(authenticationToken);
    }

    // 인증에 성공할 시 호출됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        String name = authResult.getName();
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        List<String> roles = authorities.stream().map(auth -> auth.toString()).toList();

        // AccessToken 생성
        String accessToken = delegateAccessToken(name, roles);

        // Refresh Token 생성
        String refreshToken = delegateRefreshToken(name);

        // 응답으로 돌려줄 Response의 Header에 Access Token 추가
        response.setHeader("Authorization", "Bearer " + accessToken);

        // 응답으로 돌려줄 Response의 Header에 Refresh Token 추가
        response.setHeader("Refresh", refreshToken);

        // Success Handler 호출
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    // Access Token 생성 함수
    private String delegateAccessToken(String name, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("roles", roles);

        String subject = name;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // Refresh Token 생성 함수
    private String delegateRefreshToken(String name) {
        String subject = name;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}
