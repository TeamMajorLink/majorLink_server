package com.example.majorLink.service;

import com.example.majorLink.domain.Education;
import com.example.majorLink.domain.ProfileCard;
import com.example.majorLink.domain.Project;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.CheckStatus;
import com.example.majorLink.dto.request.EducationRequest;
import com.example.majorLink.dto.request.ProfileCardRequest;
import com.example.majorLink.dto.request.ProjectRequest;
import com.example.majorLink.dto.response.EducationResponse;
import com.example.majorLink.dto.response.ProfileCardResponse;
import com.example.majorLink.dto.response.ProjectResponse;
import com.example.majorLink.repository.EducationRepository;
import com.example.majorLink.repository.ProfileCardRepository;
import com.example.majorLink.repository.ProjectRepository;
import com.example.majorLink.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileCardServiceImpl implements ProfileCardService{
    private final ProfileCardRepository profileCardRepository;
    private final EducationRepository educationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createProfileCard(User user, ProfileCardRequest request) {
        profileCardRepository.findByUser(user)
                .ifPresent(profileCard -> {
            throw new RuntimeException("이미 프로필 카드를 만들었습니다.");
        });

        ProfileCard profileCard = ProfileCard.builder()
                .lineInfo(request.getLineInfo())
                .selfInfo(request.getSelfInfo())
                .skills(request.getSkills())
                .portfolio(request.getPortfolio())
                .link(request.getLink())
                .user(user)
                .build();

        profileCardRepository.save(profileCard);

        List<Education> educations = request.getEducations().stream()
                .map(educationRequest -> Education.builder()
                        .eduName(educationRequest.getEduName())
                        .process(educationRequest.getProcess())
                        .start(educationRequest.getStart())
                        .end(educationRequest.getEnd())
                        .checkStatus(educationRequest.getCheckStatus() ? CheckStatus.CHECK : CheckStatus.UNCHECK)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        educationRepository.saveAll(educations);

        List<Project> projects = request.getProjects().stream()
                .map(projectRequest -> Project.builder()
                        .projectName(projectRequest.getProjectName())
                        .space(projectRequest.getSpace())
                        .start(projectRequest.getStart())
                        .end(projectRequest.getEnd())
                        .checkStatus(projectRequest.getCheckStatus() ? CheckStatus.CHECK : CheckStatus.UNCHECK)
                        .projectDescript(projectRequest.getProjectDescript())
                        .user(user)
                        .build())
                .collect(Collectors.toList());
        projectRepository.saveAll(projects);
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
        if (request.getSkills() != null) {
            profileCard.updateSkills(request.getSkills());
        }
        if (request.getPortfolio() != null) {
            profileCard.updatePortfolio(request.getPortfolio());
        }
        if (request.getLink() != null) {
            profileCard.updateLink(request.getLink());
        }

        profileCardRepository.save(profileCard);
        updateEducations(user, request.getEducations());
        updateProjects(user, request.getProjects());


    }
    // 상위 메서드에서 트랜잭션 처리 중임으로 추가 X
    private void updateEducations(User user, List<EducationRequest> educations) {
        if (educations == null) {
            throw new RuntimeException("교육 정보가 없습니다.");
        }

        // 현재 profile-card의 교육 정보
        List<Education> currentEducations = educationRepository.findByUser(user);

        // 현재 교육 정보을 id를 기준으로 맵으로 변환해 한꺼번에 조회
        Map<Long, Education> educationMap = currentEducations.stream()
                .collect(Collectors.toMap(Education::getId, e -> e));

        // 새로 추가하거나 업데이트할 교육 기록을 저장할 리스트
        List<Education> updatedEducations = new ArrayList<>();

        for (EducationRequest request : educations) {
            Education education;
            if (request.getId() != null && educationMap.containsKey(request.getId())) {
                // id가 education Map에 포함되어 있다면 리스트 업데이트
                education = educationMap.get(request.getId());
                education.updateEduName(request.getEduName());
                education.updateProcess(request.getProcess());
                education.updateStart(request.getStart());
                education.updateEnd(request.getEnd());
                education.updateCheckStatus(request.getCheckStatus() ? CheckStatus.CHECK : CheckStatus.UNCHECK);
            } else {
                // id가 없고, 기존 기록에 없다면 새롭게 교육 생성
                education = Education.builder()
                        .eduName(request.getEduName())
                        .process(request.getProcess())
                        .start(request.getStart())
                        .end(request.getEnd())
                        .checkStatus(request.getCheckStatus() ? CheckStatus.CHECK : CheckStatus.UNCHECK)
                        .user(user)
                        .build();
            }
            updatedEducations.add(education);
        }

        // 새로 제공된 수정 리스트에 db에 존재하는 교육이 없다면, 그 교육을 db에서 삭제
        Set<Long> requestIds = educations.stream()
                .map(EducationRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<Education> deleteEducations = currentEducations.stream()
                .filter(e -> !requestIds.contains(e.getId()))
                .collect(Collectors.toList());

        // 새로 생성되거나 업데이트 된 교육을 db에 저장
        educationRepository.saveAll(updatedEducations);

        // 리스트에 존재하지 않는 교육 삭제
        educationRepository.deleteAll(deleteEducations);
    }

    private void updateProjects(User user, List<ProjectRequest> projects) {
        if (projects == null) {
            throw new RuntimeException("프로젝트 정보가 없습니다.");
        }

        // 현재 profile-card의 교육 정보
        List<Project> currentProjects = projectRepository.findByUser(user);

        // 현재 교육 정보을 id를 기준으로 맵으로 변환해 한꺼번에 조회
        Map<Long, Project> projectMap = currentProjects.stream()
                .collect(Collectors.toMap(Project::getId, e -> e));

        // 새로 추가하거나 업데이트할 교육 기록을 저장할 리스트
        List<Project> updatedProjects = new ArrayList<>();

        for (ProjectRequest request : projects) {
            Project project;
            if (request.getId() != null && projectMap.containsKey(request.getId())) {
                // id가 education Map에 포함되어 있다면 리스트 업데이트
                project = projectMap.get(request.getId());
                project.updateProjectName(request.getProjectName());
                project.updateSpace(request.getSpace());
                project.updateStart(request.getStart());
                project.updateEnd(request.getEnd());
                project.updateCheckStatus(request.getCheckStatus() ? CheckStatus.CHECK: CheckStatus.UNCHECK);
                project.updateProjectDescript(request.getProjectDescript());
            } else {
                // id가 없고, 기존 기록에 없다면 새롭게 교육 생성
                project = Project.builder()
                        .projectName(request.getProjectName())
                        .space(request.getSpace())
                        .start(request.getStart())
                        .end(request.getEnd())
                        .checkStatus(request.getCheckStatus() ? CheckStatus.CHECK: CheckStatus.UNCHECK)
                        .projectDescript(request.getProjectDescript())
                        .user(user)
                        .build();
            }
            updatedProjects.add(project);
        }

        // 새로 제공된 수정 리스트에 db에 존재하는 교육이 없다면, 그 교육을 db에서 삭제
        Set<Long> requestIds = projects.stream()
                .map(ProjectRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<Project> deleteProjects = currentProjects.stream()
                .filter(e -> !requestIds.contains(e.getId()))
                .collect(Collectors.toList());

        // 새로 생성되거나 업데이트 된 교육을 db에 저장
        projectRepository.saveAll(updatedProjects);

        // 리스트에 존재하지 않는 교육 삭제
        projectRepository.deleteAll(deleteProjects);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileCardResponse getProfileCard(User user, String nickname) {
        ProfileCard profileCard;
        List<Education> educations;
        List<Project> projects;

        // user 인증 토큰이 없는 경우
        if (user != null) {
            profileCard = profileCardRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("프로필 카드를 등록하지 않았습니다."));
            educations = educationRepository.findByUser(user);
            projects = projectRepository.findByUser(user);
        } else {
            profileCard = profileCardRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("해당 유저가 프로필 카드를 등록하지 않았습니다."));     // 동명이인 문제로 username으로 구분 불가(userId로 구분)
            educations = educationRepository.findByNickname(nickname);
            projects = projectRepository.findByNickname(nickname);
            user = userRepository.findByNickname(nickname);
        }

        return ProfileCardResponse.builder()
                .nickname(user.getNickname())
                .firstMajor(user.getFirstMajor())
                .lineInfo(profileCard.getLineInfo())
                .selfInfo(profileCard.getSelfInfo())
                .educations(educations.stream()
                        .map(e -> EducationResponse.builder()
                                .educationId(e.getId())
                                .eduName(e.getEduName())
                                .process(e.getProcess())
                                .start(e.getStart())
                                .end(e.getEnd())
                                .checkStatus(e.getCheckStatus() == CheckStatus.CHECK)
                                .build())
                        .collect(Collectors.toList()))
                .projects(projects.stream()
                        .map(p -> ProjectResponse.builder()
                                .projectId(p.getId())
                                .projectName(p.getProjectName())
                                .space(p.getSpace())
                                .start(p.getStart())
                                .end(p.getEnd())
                                .checkStatus(p.getCheckStatus() == CheckStatus.CHECK)
                                .projectDescript(p.getProjectDescript())
                                .build()
                        ).collect(Collectors.toList()))
                .skills(profileCard.getSkills())
                .portfolio(profileCard.getPortfolio())
                .link(profileCard.getLink())
                .build();
    }

}
