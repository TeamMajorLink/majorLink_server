package com.example.majorLink.service;

import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.CreateProfileCardRequest;
import com.example.majorLink.repository.ProfileCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileCardServiceImpl implements ProfileCardService{
    private final ProfileCardRepository profileCardRepository;

    @Override
    @Transactional
    public void createProfileCard(User user, CreateProfileCardRequest request) {
        profileCardRepository.findByUser(user)
                .ifPresent(profileCard -> {
            throw new RuntimeException("이미 프로필 카드를 만들었습니다.");
        });
        ProfileCard profileCard = ProfileCard.builder()
                .info(request.getInfo())
                .grade(request.getGrade())
                .career(request.getCareer())
                .portfolio(request.getPortfolio())
                .link(request.getLink())
                .build();

        profileCardRepository.save(profileCard);
    }
}
