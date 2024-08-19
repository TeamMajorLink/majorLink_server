package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.Day;
import com.example.majorLink.domain.enums.Exam;
import com.example.majorLink.domain.enums.Level;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Lecture extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 1000)
    private String body;

    @Column(nullable = false)
    private Integer curri;

    @Column(nullable = false, length = 1000)
    private String info;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private Level level;

    // 총 인원
    @Column(nullable = false)
    private Integer pNum;

    // 현재 신청 인원
    @Column(nullable = false)
    private Integer cNum;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Day day;

    @Column(nullable = false)
    private Date startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 20)
    private String tag;

    @Column(nullable = false, length = 100)
    private String tutor;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateLecture(String name, String body, int curri, String info, Level level, int pNum, LocalTime time, Day day, Date startDate, Exam exam, Category category, String tag ){
        this.name = name;
        this.body = body;
        this.curri = curri;
        this.info = info;
        this.level = level;
        this.pNum = pNum;
        this.time = time;
        this.day = day;
        this.startDate = startDate;
        this.exam = exam;
        this.category = category;
        this.tag = tag;
    }

    public void addCurPNum(){
        this.cNum++;
    }

    public void subCurPNum(){
        this.cNum--;
    }
}