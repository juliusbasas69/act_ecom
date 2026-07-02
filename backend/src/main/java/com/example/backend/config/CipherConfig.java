package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.example.backend.common.util.CipherUtil;

import jakarta.annotation.PostConstruct;

@Configuration
public class CipherConfig {

    @Value("${cipher.algorithm}")
    private String algorithm;

    @Value("${cipher.key}")
    private String secret;

    @PostConstruct
    public void init() {
        CipherUtil.init(algorithm, secret);
    }
}
