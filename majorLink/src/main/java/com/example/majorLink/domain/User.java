package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.Gender;
import com.example.majorLink.domain.enums.LearnPart;
import com.example.majorLink.domain.enums.UserStatus;
import com.example.majorLink.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(name = "phone", length = 40)
    private String phone;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "firstMajor", nullable = false, length = 40)
    private String firstMajor;

    @Column(name = "secondMajor", length = 40)
    private String secondMajor;

    @Column(length = 40)
    private String favorite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'TUTEE'")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "learnPart", nullable = false, columnDefinition = "VARCHAR(20)")
    private LearnPart learnPart;

    @Column(nullable = false,  columnDefinition = "INT DEFAULT 0")
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(name = "userStatus", columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
    private UserStatus userStatus;

    public void updateEmail(String email) {
        this.email = email;
    }
}
