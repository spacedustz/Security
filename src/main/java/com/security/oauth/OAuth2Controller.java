package com.security.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {

    @GetMapping("/oauth2")
    public String home() {
        return "oauth2";
    }
}
