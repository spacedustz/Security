package com.security.config;

import com.security.filter.JwtAuthenticationFilter;
import com.security.jwt.JwtTokenizer;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;

    /**
     * headers = Same Origin에서의 Request만 허용
     * csrf = 세션을 사용하지 않기 떄문에 비활성화
     * formLogin = 비활성화
     * httpBasic = 비활성화
     * apply = CustomFilter 추가 (JWT)
     * authorizeHttpRequests =- URL 별로 ACL 설정
     * @param http : HttpSecurity
     * @return : SecurityFilterChain
     * @throws Exception : Exception
     * @desc : Security Filter Chain 구성, HTTP Request에 대한 보안 설정
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);

        return http
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .logout((logout) -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/**").hasRole("USER")
                )
                .build();
    }

    // Password Encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // CORS Policy
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));   // 모든 Origin에 대해 HTTP 통신 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));  // 허용할 HTTP Method

        // CorsConfigurationSource 인터페이스읙 구현 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 URL에 위의 CORS 정책 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Custom Configurer, JwtAuthenticationFilter 등록 - AbstractHttpConfigure를 상속해서 구현할 수 있다.
    public class CustomFilter extends AbstractHttpConfigurer<CustomFilter, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            // getSharedObject를 통해 AuthenticationManager 객체를 얻을 수 있음
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            // JwtAuthenticationFilter을 생성하면서 이 Filter 에서 사용되는 Manager,Tokenizer를 넣어줌
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);

            // Security Filter에 추가
            builder.addFilter(jwtAuthenticationFilter);
        }
    }
}
