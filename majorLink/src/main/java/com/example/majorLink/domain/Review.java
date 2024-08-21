package com.example.majorLink.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private int rate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    public void setUser(User user){
        this.user = user;
    }

    public void setLecture(Lecture lecture){
        this.lecture = lecture;
    }

    public void updateReview(String content, int rate){

        this.content = content;
        this.rate = rate;
    }
}
