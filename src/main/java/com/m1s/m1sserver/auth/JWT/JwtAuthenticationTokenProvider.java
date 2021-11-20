package com.m1s.m1sserver.auth.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtAuthenticationTokenProvider implements AuthenticationTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenProvider.class);

    @Value("accessPrivateKey")
    @Getter
    private static String ACCESS_PRIVATE_KEY;


    @Value("refreshPrivateKey")
    @Getter
    public static String REFRESH_PRIVATE_KEY;


    @Value("accessTokenExpirationMS")
    public static Long ACCESS_TOKEN_EXPIRATION_MS;


    @Value("refreshTokenExpirationMS")
    public static Long REFRESH_TOKEN_EXPIRATION_MS;


    @Override
    public HashMap<String, String> parseTokenString(HttpServletRequest request) {
        if(request.getHeader("x-access-token") == null || request.getHeader("x-refresh-token") == null)return null;
        HashMap<String, String> hm = new HashMap<>();
        hm.put("x-access-token",request.getHeader("x-access-token"));
        hm.put("x-refresh-token", request.getHeader("x-refresh-token"));
        return hm;
    }
    private String buildToken(Long userId, Long EXPIRATION_MS){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(EXPIRATION_MS, ChronoUnit.MILLIS);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(ACCESS_PRIVATE_KEY.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public AuthenticationToken issue(Long user_id){
        return JwtAuthenticationToken.builder()
                .accessToken(buildToken(user_id,ACCESS_TOKEN_EXPIRATION_MS))
                .refreshToken(buildToken(null, REFRESH_TOKEN_EXPIRATION_MS))
                .build();
    };

    @Override
    public Long getTokenOwnerNo(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_PRIVATE_KEY).build()
                .parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    };

    @Override
    public boolean validateToken(String token, String PRIVATE_KEY){
        if(!token.isEmpty()){
            try{
                Jwts.parserBuilder().setSigningKey(PRIVATE_KEY).build()
                        .parseClaimsJws(token);
                return true;
            }catch(Exception e){//헤더, 페이로드, 시그니쳐 중 시그니쳐가 해석 불가능할 때
                return false;
            }
        }
        return false;
    };

}
