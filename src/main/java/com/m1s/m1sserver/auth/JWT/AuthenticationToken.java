package com.m1s.m1sserver.auth.JWT;


import lombok.Builder;
import lombok.Getter;

public interface AuthenticationToken {

    String getAccessToken();
    String getRefreshToken();
}