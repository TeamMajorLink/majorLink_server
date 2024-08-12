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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 스킬 종속 컬렉션 테이블 생성
    @ElementCollection(fetch = FetchType.LAZY)
    @JoinTable(name = "skills")
    private List<String> skills = new ArrayList<>();

    public void updateLineInfo(String lineInfo) {
        this.lineInfo = lineInfo;
    }
    public void updateSelfInfo(String selfInfo) {
        this.selfInfo = selfInfo;
    }
    public void updateSkills(List<String> skills) {
        this.skills.clear(); // 기존 기술 삭제
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }
    public void updatePortfolio(String portfolio) {
        this.portfolio = portfolio;
    }
    public void updateLink(String link) {
        this.link = link;
    }
}
