package com.example.settingtest.config.security;

import com.example.settingtest.constant.Role;
import com.example.settingtest.domain.Member;
import com.example.settingtest.repository.jpa.MemberJpaRepository;
import com.google.gson.Gson;
import com.example.settingtest.repository.cache.CacheRepository;
import com.example.settingtest.utils.LocalDateTimeZoneUtil;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "TEST_APP_API_SERVER";
    private static final int LOGIN_SESSION_EXTEND_MINUTES = 120;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final CacheRepository cacheRepository;
    private final Gson gson;

    @Autowired
    public JwtTokenProvider(MemberJpaRepository memberRepository,
                            @Qualifier("redisRepository") CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
        this.gson = new Gson();
    }

    public String generateJwtToken(Member member) {
        LocalDateTime expireDate = LocalDateTimeZoneUtil.getNow().plusMinutes(LOGIN_SESSION_EXTEND_MINUTES);
        JwtBuilder builder = Jwts.builder()
                .setSubject(member.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(member, expireDate))
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            Member member = gson.fromJson(claims.get("claims").toString(), Member.class);
            return Objects.nonNull(member);
        } catch (ExpiredJwtException exception) {
            return false;
        } catch (JwtException exception) {
            return false;
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public Authentication createAuthenticationFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        Member member = gson.fromJson(claims.get("claims").toString(), Member.class);
        return new TestAuthenticationToken(member.getEmail(), member.getPassword(), member.getAuthorities());
    }

    public String getTokenFromHeader(String header) {
        return header;
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createClaims(Member member, LocalDateTime expireDt) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getEmail());
        claims.put("claims", gson.toJson(member));
        return claims;
    }

    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token)
                .getBody();
    }

    public void extendSession(String token) {
        Claims claims = getClaimsFormToken(token);
        Member account = gson.fromJson(claims.get("claims").toString(), Member.class);
        String sessionKey = "";
        if (account.getRole() == Role.ADMIN) {
            sessionKey = "ADMIN:";
        } else {
            sessionKey = "USER:";
        }

        cacheRepository.setValue(sessionKey + account.getEmail(), String.valueOf(account.getId()),
                LOGIN_SESSION_EXTEND_MINUTES);
    }
}
