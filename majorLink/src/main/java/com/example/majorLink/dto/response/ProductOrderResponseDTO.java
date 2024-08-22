package com.example.majorLink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderResponseDTO {

    private String product;
    private String merchantUid;
    private BigDecimal amount;
}
