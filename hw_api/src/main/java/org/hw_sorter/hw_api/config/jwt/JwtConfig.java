package org.hw_sorter.hw_api.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.hw_sorter.hw_api.global.exception.JwtExpiredException;
import org.hw_sorter.hw_api.global.exception.JwtValidationFailException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtConfig {

    // JWT 시크릿 키 (임의로 생성하거나 보안 관련 설정에 따라 관리)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

//    private static final String SALT = "omeye_reid_20231025100000_ver_2_token_for_jwt";

    // JWT 유효 기간 (30일)
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 3600000L; // 1 hour

    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 3600000L * 24 * 7; // 1일

    // JWT 생성
    public static <T> String createJwt(JwtType jwtType, String key, T data) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + getTokenExpireTime(jwtType));

        Map<String, Object> header = new HashMap<>();
        header.put("type", jwtType);

        String value = null;

        try {
            value = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return "Bearer " + Jwts.builder()
                .setHeader(header)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .claim(key, value)
                .signWith(SECRET_KEY)
                .compact();
    }

    // JWT 검증
    public static Claims getClaimsFromJwt(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            // JWT 검증 실패
            log.info("cannot parse claim from jwt");
            throw new JwtValidationFailException();
        }
    }

//    public static JwsHeader<?> getHeader(String jwt) {
//        return (JwsHeader<?>) Jwts.parserBuilder()
//                .setSigningKey(SALT.getBytes(StandardCharsets.UTF_8))
//                .build()
//                .parseClaimsJws(jwt)
//                .getHeader();
//    }

    public static <T> T getPayloadFromJwt(String jwt, String key, Class<T> payloadClass) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return new ObjectMapper().readValue(claims.get(key, String.class), payloadClass);
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException();
        } catch (Exception e) {
            log.error("exception trace : ", e);
            throw new JwtValidationFailException();
        }
    }

    public static LocalDateTime getExpireTime(String accessToken) {
        return Instant.ofEpochMilli(getClaimsFromJwt(accessToken).getExpiration().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private static long getTokenExpireTime(JwtType jwtType) {
        return switch (jwtType) {
            case ACCESS -> ACCESS_TOKEN_EXPIRATION_TIME;
            case REFRESH -> REFRESH_TOKEN_EXPIRATION_TIME;
        };

    }

    public enum JwtType {
        ACCESS, REFRESH
    }

}
