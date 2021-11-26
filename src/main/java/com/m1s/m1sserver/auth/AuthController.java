package com.m1s.m1sserver.auth;


import com.m1s.m1sserver.UserStorage;
import com.m1s.m1sserver.api.user.information.MemberInformation;
import com.m1s.m1sserver.auth.JWT.AuthenticationToken;
import com.m1s.m1sserver.auth.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    UserStorage userStorage;

    @PostMapping("/join")
    public MemberInformation join(@RequestBody MemberInformation memberInformation){
            return authService.join(memberInformation);
    }

    @DeleteMapping("/me")
    public void deleteAccount(Authentication authentication){
        Member me = authService.getMe(authentication);
        authService.deleteAccount(me);
    }

    @GetMapping("/login")
    public AuthenticationToken login(@RequestBody Member member){
        return authService.login(member);
    }

    @PostMapping("/logout")
    public boolean logout(){
        return authService.logout(userStorage.getMember());
    }

}
