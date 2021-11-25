package com.m1s.m1sserver.auth.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Getter
    private static final String ACCESS_PRIVATE_KEY = "accessPrivateKeydkdoldoan;asdflkjaf";


    @Getter
    public static final String REFRESH_PRIVATE_KEY = "refreshPrivateKeyasdfjlasfdjohyeonchang";


    public static final Long ACCESS_TOKEN_EXPIRATION_MS = 1000000L;


    public static final Long REFRESH_TOKEN_EXPIRATION_MS = 1000000L;


    @Override
    public HashMap<String, String> parseTokenString(HttpServletRequest request) {
        //TODO refcell 22- refreshtoken 관련부분은 알아서 구현. refreshtoken은 평소에 null이다가 갑자기 넘길때는 클라이언트의 갱신요청으로 인식하고 따로처리해야하는데 여기서 다 검사해버리면 안됨
//        if(request.getHeader("x-access-token") == null || request.getHeader("x-refresh-token") == null)return null;
        if(request.getHeader("x-access-token") == null)return null;
        HashMap<String, String> hm = new HashMap<>();
        hm.put("x-access-token",request.getHeader("x-access-token"));
//        hm.put("x-refresh-token", request.getHeader("x-refresh-token"));
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


    /**
     * 토큰을 해석하면서 에러도 같이 검사하므로 궂이 validation을 따로 할 필요는 없음.
     * 어차피 해석해서 SpringSecurity가 이해할수있는 Authentication으로 바꿔주어야함
     * edited by refcell22
     */
    @Override
    public Jws<Claims> parseToken(String token, String PRIVATE_KEY){
        if(!token.isEmpty()){
            try{
                return Jwts.parserBuilder().setSigningKey(PRIVATE_KEY).build()
                        .parseClaimsJws(token);
            }catch(Exception e){//헤더, 페이로드, 시그니쳐 중 시그니쳐가 해석 불가능할 때
                return null;
            }
        }
        return null;
    }
}
