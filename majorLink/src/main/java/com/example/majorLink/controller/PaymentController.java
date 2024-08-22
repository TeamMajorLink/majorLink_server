package com.example.majorLink.controller;

import com.example.majorLink.domain.ProductOrder;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.PaymentStatus;
import com.example.majorLink.dto.request.PaymentRequestDTO;
import com.example.majorLink.dto.request.ProductOrderRequestDTO;
import com.example.majorLink.dto.response.PaymentResponseDTO;
import com.example.majorLink.dto.response.ProductOrderResponseDTO;
import com.example.majorLink.global.auth.AuthUser;
import com.example.majorLink.service.PaymentService.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 요청
    @PostMapping("/order")
    @ResponseBody
    public ProductOrderResponseDTO prepareProductOrder(@AuthenticationPrincipal AuthUser authUser,
                                                       @RequestBody ProductOrderRequestDTO request) throws IamportResponseException, IOException {
        User user = authUser.getUser();
        ProductOrder productOrder = paymentService.prepareProductOrder(user.getId(), request);

        return ProductOrderResponseDTO.builder()
                .product(String.valueOf(productOrder.getProduct()))
                .merchantUid(productOrder.getMerchantUid())
                .amount(productOrder.getAmount())
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
    }

    // 결제 검증
    @PostMapping("/verify")
    @ResponseBody
    public PaymentResponseDTO validatePayment(@AuthenticationPrincipal AuthUser authUser,
                                              @RequestBody PaymentRequestDTO request) throws IamportResponseException, IOException {

        User user = authUser.getUser();
        Boolean isValid = paymentService.validatePayment(user.getId(), request);

        return PaymentResponseDTO.builder()
                .paymentStatus(isValid ? PaymentStatus.PAID : PaymentStatus.FAILED)
                .amount(request.getAmount())
                .build();
    }

}