package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.LearnPart;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mainCategory", nullable = false, columnDefinition = "VARCHAR(20)")
    private LearnPart learnPart;

    @Column(nullable = false, length = 20)
    private String subCategory;

}
