package com.example.majorLink.dto.response;

import com.example.majorLink.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    PaymentStatus paymentStatus;
    BigDecimal amount;
}