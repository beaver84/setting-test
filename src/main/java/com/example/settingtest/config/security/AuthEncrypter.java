package com.example.settingtest.config.security;

import com.example.settingtest.config.exception.ApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AuthEncrypter {

	private Logger log = LogManager.getLogger(this.getClass());

    private AuthEncryptKeyFactory keyFactory;

    @Autowired
    @Qualifier("authEncryptKeyFactory")
    private void setKeyFactory(AuthEncryptKeyFactory encryptKeyFactory) {
        this.keyFactory = encryptKeyFactory;
    }

    public String encrypt(String targetString) {
        String encryptString = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.getPublicKey());
            byte[] encrypted = cipher.doFinal(targetString.getBytes(StandardCharsets.UTF_8));
            encryptString = Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
        	log.error("Tartget String -> {}", targetString);
        	log.error("암호화중 오류가 발생했습니다 : {}", e.getMessage());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "암호화중 오류가 발생하였습니다.");
        }

        return encryptString;
    }

    public String decrypt(String targetString) {
        String decryptString = "";
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.getPrivateKey());
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(targetString));
            decryptString = new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
        	log.error("Tartget String -> {}", targetString);
        	log.error("암호화중 오류가 발생했습니다 : {}", e.getMessage());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "복호화중 오류가 발생하였습니다.");
        }

        return decryptString;
    }
}
