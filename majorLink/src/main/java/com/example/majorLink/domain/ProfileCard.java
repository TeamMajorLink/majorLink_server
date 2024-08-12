package com.example.majorLink.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileCard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lineInfo;
    private String selfInfo;
    private String portfolio;
    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profileCard_id")
    private List<Education> educations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profileCard_id")
    private List<Project> projects = new ArrayList<>();

    // 스킬 종속 컬렉션 테잉블 생성
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "skills")
    private List<String> skills = new ArrayList<>();

    public void updateLineInfo(String lineInfo) {
        this.lineInfo = lineInfo;
    }
    public void updateSelfInfo(String selfInfo) {
        this.selfInfo = selfInfo;
    }
    public void updateEducations(List<Education> education) {
        this.educations = educations;
    }
    public void updateProjects(List<Project> projects) {
        this.projects = projects;
    }
    public void updateSkills(List<String> skill) {
        this.skills = skills;
    }
    public void updatePortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
    public void updateLink(String link) {
        this.link = link;
    }
}
