package com.m1s.m1sserver.auth.JWT;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface AuthenticationTokenProvider {
    HashMap<String, String> parseTokenString(HttpServletRequest request);
    AuthenticationToken issue(Long user_id);
    Long getTokenOwnerNo(String token);
    boolean validateToken(String token, String PRIVATE_KEY);
}
