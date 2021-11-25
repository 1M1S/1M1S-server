package com.m1s.m1sserver.auth.JWT;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class JwtAuthenticationToken implements AuthenticationToken {
    private String accessToken;
    private String refreshToken;
}
