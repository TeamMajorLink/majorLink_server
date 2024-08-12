package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.CheckStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

}