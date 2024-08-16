package com.example.majorLink.dto.request;

import com.example.majorLink.domain.enums.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderRequestDTO {

    private Product product;
    private BigDecimal amount;
}
