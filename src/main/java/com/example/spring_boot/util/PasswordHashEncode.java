package com.example.spring_boot.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public final class PasswordHashEncode {

    public static String encode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}

