package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.Day;
import com.example.majorLink.domain.enums.Exam;
import com.example.majorLink.domain.enums.Level;
import com.example.majorLink.domain.mapping.Bookmark;
import com.example.majorLink.domain.mapping.Liked;
import com.example.majorLink.domain.mapping.UserLecture;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private int curri;

    @Column(nullable = false, length = 1000)
    private String info;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Level level;

    @Column(nullable = false)
    private int pNum;

    private LocalTime time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Day day;

    @Column(nullable = false)
    private Date startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    /*@OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<UserLecture> userLectureList = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarkList = new ArrayList<>();*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updateLecture(String name, String body, int curri, String info, Level level, int pNum, LocalTime time, Day day, Date startDate, Exam exam, Category category, Tag tag ){
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
}