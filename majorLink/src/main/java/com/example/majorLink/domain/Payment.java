package com.example.majorLink.domain;

import com.example.majorLink.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 결제 금액
    @Column(nullable = false)
    private BigDecimal amount;

    // 주문 고유번호
    @Column(nullable = false)
    private String merchantUid;

    // 결제 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'READY'")
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void updatePaymentStatus(PaymentStatus newStatus) {
        this.paymentStatus = newStatus;
    }
}