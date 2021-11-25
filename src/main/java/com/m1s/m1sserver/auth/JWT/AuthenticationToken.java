package com.m1s.m1sserver.auth.JWT;


public interface AuthenticationToken {

    String getAccessToken();
    String getRefreshToken();
}