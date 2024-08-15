package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.CheckStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Education extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String eduName;
    @Column(nullable = false, length = 100)
    private String process;
    private String start;
    private String end;
    @Enumerated(EnumType.STRING)
    private CheckStatus checkStatus = CheckStatus.UNCHECK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateEduName(String eduName) {
        this.eduName = eduName;
    }
    public void updateProcess(String process) {
        this.process = process;
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

}