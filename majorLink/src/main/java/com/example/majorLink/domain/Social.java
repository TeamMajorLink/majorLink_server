package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.SocialStatus;
import com.example.majorLink.domain.enums.SocialType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Social extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(name = "socialStatus", nullable = false, columnDefinition = "VARCHAR(30) DEFAULT 'CONNECTED'")
    private SocialStatus socialStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String providerId;

    public void updateSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = SocialStatus.CONNECTED;
    }
    public void updateUser(User user) {
        this.user = user;
    }

}