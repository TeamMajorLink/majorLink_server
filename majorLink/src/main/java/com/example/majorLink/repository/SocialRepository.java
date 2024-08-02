package com.example.majorLink.repository;

import com.example.majorLink.domain.Social;
import com.example.majorLink.domain.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social, Long> {
    Optional<Social> findBySocialTypeAndProviderId(SocialType socialType, Long providerId);

}
