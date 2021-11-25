package com.m1s.m1sserver.auth.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

/**
 * created by refcell22 at 2021-11-25 (refcell22@naver.com)
 */
@AllArgsConstructor
public class JwtAuthentication implements Authentication {
    Jws<Claims> claims;

    /**
     * refcell22 - 여기서 claims에 넣어놓은 권한에 따라 SpringSecurity가 이해할 수 있는 GratendAuthority로 변환해주어야함
     * claims에 만약 admin요소의 값이 true이면 admin권한을 추가하는 예시를 코드로 넣어놓았으니 확인
     * @return SpringSecurity가 이해할 수 있는 권한목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Boolean admin = (Boolean) claims.getBody().get("admin");
        if(admin != null && admin) authorities.add(new SimpleGrantedAuthority("admin"));
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return claims;
    }

    @Override
    public Object getPrincipal() {
        return claims;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
