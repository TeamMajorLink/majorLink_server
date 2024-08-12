package com.example.majorLink.service;

import com.example.majorLink.domain.Education;
import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.Project;
import com.example.majorLink.domain.User;
import com.example.majorLink.dto.request.ProfileCardRequest;
import com.example.majorLink.repository.ProfileCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileCardServiceImpl implements ProfileCardService{
    private final ProfileCardRepository profileCardRepository;

    @Override
    @Transactional
    public void createProfileCard(User user, ProfileCardRequest request) {
        profileCardRepository.findByUser(user)
                .ifPresent(profileCard -> {
            throw new RuntimeException("이미 프로필 카드를 만들었습니다.");
        });
        List<Education> educations = request.getEducations().stream()
                .map(educationRequest -> Education.builder()
                        .eduName(educationRequest.getEduName())
                        .process(educationRequest.getProcess())
                        .start(educationRequest.getStart())
                        .end(educationRequest.getEnd())
                        .checkStatus(educationRequest.getCheckStatus())
                        .build())
                .collect(Collectors.toList());


        List<Project> projects = request.getProjects().stream()
                .map(projectRequest -> Project.builder()
                        .projectName(projectRequest.getProjectName())
                        .space(projectRequest.getSpace())
                        .start(projectRequest.getStart())
                        .end(projectRequest.getEnd())
                        .checkStatus(projectRequest.getCheckStatus())
                        .projectDescript(projectRequest.getProjectDescript())
                        .build())
                .collect(Collectors.toList());

        ProfileCard profileCard = ProfileCard.builder()
                .lineInfo(request.getLineInfo())
                .selfInfo(request.getSelfInfo())
                .educations(educations)
                .projects(projects)
                .projects(request.getProjects())
                .skills(request.getSkills())
                .portfolio(request.getPortfolio())
                .link(request.getLink())
                .user(user)
                .build();

        profileCardRepository.save(profileCard);
    }

    @Override
    @Transactional
    public void modifyProfileCard(User user, ProfileCardRequest request) {
        ProfileCard profileCard = profileCardRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("등록된 프로필 카드가 없습니다."));
        if (request.getLineInfo() != null) {
            profileCard.updateLineInfo(request.getLineInfo());
        }
        if (request.getSelfInfo() != null) {
            profileCard.updateSelfInfo(request.getSelfInfo());
        }
//        if (request.getSkill() != null) {
//            profileCard.updateSkill(request.getSkill());
//        }
        if (request.getPortfolio() != null) {
            profileCard.updatePortfolio(request.getPortfolio());
        }
        if (request.getLink() != null) {
            profileCard.updateLink(request.getLink());
        }
        profileCardRepository.save(profileCard);
    }
}
