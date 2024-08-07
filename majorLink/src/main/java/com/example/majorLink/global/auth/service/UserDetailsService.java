package com.example.majorLink.global.auth.service;

import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.UserStatus;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByIdAndUserStatusIs(UUID.fromString(username), UserStatus.ACTIVE).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new AuthUser(user);
    }
}
