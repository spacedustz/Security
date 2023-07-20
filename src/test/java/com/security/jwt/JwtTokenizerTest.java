package com.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Access Token 생성 테스트")
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
    @DisplayName("Refresh Token 생성 테스트")
    public void generateRefreshTokenTest() {
        String subject = "Test Refresh Token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        Date expiration = calendar.getTime();

        String refreshToken = tokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        System.out.println(refreshToken);

        assertThat(refreshToken, notNullValue());
    }

    @Test
    @DisplayName("JWT Signature 검증 테스트")
    public void verifySignatureTest() {
        String accessToken = getAccessToken(Calendar.MINUTE, 10);
        assertDoesNotThrow(() -> tokenizer.verifySignature(accessToken, base64EncodedSecretKey));
    }

    @Test
    @DisplayName("Throw ExpiredJwtException When Jwt Verify")
    public void verifyExpirationTest() throws InterruptedException {
        String accessToken = getAccessToken(Calendar.SECOND, 1);
        assertDoesNotThrow(() -> tokenizer.verifySignature(accessToken, base64EncodedSecretKey));

        TimeUnit.MILLISECONDS.sleep(1500);

        assertThrows(ExpiredJwtException.class, () -> tokenizer.verifySignature(accessToken, base64EncodedSecretKey));
    }

    // Access Token 생성 함수
    private String getAccessToken(int timeUnit, int timeAmount) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1L);
        claims.put("roles", List.of("USER"));

        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(timeUnit, timeAmount);
        Date expiration = calendar.getTime();

        return tokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }
}
