package com.security.cloudconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloud")
@RequiredArgsConstructor
public class CloudController {
    private final CloudConfig config;

    @GetMapping("/remind-security")
    public String loadSecurityConfig() {
        return config.toString();
    }
}
