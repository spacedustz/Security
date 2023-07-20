package com.security.jwt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/* Jwt Token 생성 테스트 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
    private static JwtTokenizer tokenizer;
    private String secretKey;
    private String base64EncodedSecretKey;

    @BeforeAll
    public void init() {
        tokenizer = new JwtTokenizer();
        secretKey = "skw1234123412341234123412341234123412341234";

        base64EncodedSecretKey = tokenizer.encodeBase64SecretKey(secretKey);
    }

    @Test
    public void generateAccessTokenTest() {
        // 토큰의 유저 정보
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1L);
        claims.put("roles", List.of("USER"));

        // 토큰 제목
        String subject = "Test Access Token";

        // 토큰 발행일
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);

        // 토큰 만료일
        Date expiration = calendar.getTime();

        // Access Token
        String accessToken = tokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        System.out.println(accessToken);

        assertThat(accessToken, notNullValue());
    }

    @Test
    public void generateRefreshTokenTest() {
        String subject = "Test Refresh Token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        Date expiration = calendar.getTime();

        String refreshToken = tokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        System.out.println(refreshToken);

        assertThat(refreshToken, notNullValue());
    }
}
