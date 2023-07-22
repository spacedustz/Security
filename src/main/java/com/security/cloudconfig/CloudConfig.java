package com.security.cloudconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Getter
@Component
@RefreshScope // Config.yml 파일 변경 시 변경된 내용을 actuator를 통해 변경값을 갱신
public class CloudConfig {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String oauthClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String oauthClientPw;

    @Override
    public String toString() {
        return "CloudConfig{" +
                "oauthClientId='" + oauthClientId + '\'' +
                ", oauthClientPw='" + oauthClientPw + '\'' +
                '}';
    }
}
