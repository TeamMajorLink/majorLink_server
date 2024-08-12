package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.CheckStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String projectName;
    @Column(nullable = false, length = 50)
    private String space;
    private String start;
    private String end;
    @Enumerated(EnumType.STRING)
    private CheckStatus checkStatus = CheckStatus.UNCHECK;
    @Column(nullable = false, length = 255)
    private String projectDescript;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void updateSpace(String space) {
        this.space = space;
    }
    public void updateStart(String start) {
        this.start = start;
    }
    public void updateEnd(String end) {
        this.end = end;
    }
    public void updateCheckStatus(CheckStatus checkStatus) {
        this.checkStatus = checkStatus;
    }
    public void updateProjectDescript(String projectDescript) {
        this.projectDescript = projectDescript;
    }

}