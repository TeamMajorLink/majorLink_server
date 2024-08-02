package com.example.majorLink.controller;

import com.example.majorLink.domain.User;
import com.example.majorLink.global.auth.AuthTokensGenerator;
import com.example.majorLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{accessToken}")
    public ResponseEntity<User> findByAccessToken(@PathVariable String accessToken) {
        UUID userId = authTokensGenerator.extractUserId(accessToken);
        return ResponseEntity.ok(userRepository.findById(userId).get());
    }


}
