package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.Gender;
import com.example.majorLink.domain.enums.LearnPart;
import com.example.majorLink.domain.enums.MemberStatus;
import com.example.majorLink.domain.enums.Role;
import com.example.majorLink.domain.mapping.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 40)
    private String phone;

    @Column(nullable = false, length = 40)
    private String firstMajor;

    @Column(length = 40)
    private String secondMajor;

    @Column(length = 40)
    private String favorite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20)")
    private LearnPart learnPart;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private MemberStatus status;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserImage profileImg;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Social social;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileCard profileCard;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserNotification> userNotificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserLecture> userLectureList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Liked> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserChat> chatList = new ArrayList<>();
}
