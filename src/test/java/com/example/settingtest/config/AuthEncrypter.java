package com.example.settingtest.config;

import com.example.settingtest.config.security.AuthEncrypter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthEncrypterTest {
    private final AuthEncrypter authEncrypter;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthEncrypterTest.class);

    @Autowired
    public AuthEncrypterTest(AuthEncrypter authEncrypter) {
        this.authEncrypter = authEncrypter;
    }

    @Test
    void encrypt() {
        LOGGER.debug("{}",authEncrypter.encrypt("abc"));
    }

    @Test
    void decrypt() {
        LOGGER.debug("{}", authEncrypter.decrypt("QvsAejnixKcQnxsehM888KKyUcuAFaXJI/FpunPCPCs7fNvVUOXTv34DcCZFS3jHeTi4LjiHc4qW6FH2bnpvSjMhLtBDfZN3d17PFsOWvlPiggUsOa5iBQCb5FfmreJ+uzqG/IvKoO3MA2Lds/CIBuztsmDw//iKPSkG4k+zJVs="));
    }
}
