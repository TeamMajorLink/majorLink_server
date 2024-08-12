package com.example.majorLink.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequestDTO {

    private String impUid;
    private String merchantUid;
    private BigDecimal amount;
}