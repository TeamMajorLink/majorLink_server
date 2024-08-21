package com.example.majorLink.controller;

import com.example.majorLink.domain.User;
import com.example.majorLink.global.auth.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealtyCheckController {

    @GetMapping("/health")
    public String checkHealth(@AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        return "im healty" + user;
    }

    @PostMapping("/post/health")
    public String checkPost(@AuthenticationPrincipal AuthUser authUser, @RequestBody String title) {
        User user = authUser.getUser();
        return title + user;

    }
}
