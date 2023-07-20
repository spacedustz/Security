package com.security.auth;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/* Custom한 Delegating Password Encoder */
public class CustomDelegatingPasswordEncoder {
//    String idForEncode = "bcrypt";
//    Map<Object, Object> encoders = new HashMap<>();
//    encoders.put(idForEncode, new BCryptPasswordEncoder());
//    encoders.put("noop", NoOpPasswordEncoder.getInstance());
//    encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//    encoders.put("scrypt", new SCryptPasswordEncoder());
//    encoders.put("sha256", new StandardPasswordEncoder());
//
//    PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
}
