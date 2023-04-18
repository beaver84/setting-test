package com.example.settingtest.config.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class AuthEncryptKeyFactory {

	private Logger log = LogManager.getLogger(this.getClass());

    @Value("${test.pubkey}")
    private String rawPublicKey;

    @Value("${test.prikey}")
    private String rawPrivateKey;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Autowired
    private void loadPublicKey() {
        try {
            byte[] publicKeyDER = Base64.getDecoder().decode(rawPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyDER));
        } catch (Exception e) {
        	log.error("암호화키 생성 중에 오류가 발생했습니다 : {}", e.getMessage());
            publicKey = null;
        }
    }

    @Autowired
    private void loadPrivateKey() {
        try {
            byte[] privateKeyDER = Base64.getDecoder().decode(rawPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDER));
        } catch (Exception e) {
        	log.error("암호화키 생성 중에 오류가 발생했습니다 : {}", e.getMessage());
            publicKey = null;
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

}
