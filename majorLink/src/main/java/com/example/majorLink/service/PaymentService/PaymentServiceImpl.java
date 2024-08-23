package com.example.majorLink.service.PaymentService;


import com.example.majorLink.domain.Payment;
import com.example.majorLink.domain.ProductOrder;
import com.example.majorLink.domain.User;
import com.example.majorLink.domain.enums.PaymentStatus;
import com.example.majorLink.dto.request.PaymentRequestDTO;
import com.example.majorLink.dto.request.ProductOrderRequestDTO;
import com.example.majorLink.repository.PaymentRepository;
import com.example.majorLink.repository.ProductOrderRepository;
import com.example.majorLink.repository.UserRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private IamportClient iamportClient;

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ProductOrderRepository productOrderRepository;

    // 결제 요청
    @Override
    public ProductOrder prepareProductOrder(UUID userId, ProductOrderRequestDTO request) throws IamportResponseException, IOException { // 임시 예외처리
        User user = userRepository.findById(userId).get();

        String merchantUid = user.getId().toString() + "_" + System.currentTimeMillis();
        BigDecimal amount = request.getAmount();

        ProductOrder productOrder = ProductOrder.builder()
                .product(request.getProduct())
                .amount(amount)
                .user(user)
                .merchantUid(merchantUid)
                .build();

        ProductOrder saveProductOrder = productOrderRepository.save(productOrder);

        PrepareData prepareData = new PrepareData(merchantUid, amount);

        iamportClient.postPrepare(prepareData);

        Payment payment = Payment.builder()
                        .user(user)
                        .merchantUid(merchantUid)
                        .amount(amount)
                        .paymentStatus(PaymentStatus.READY)
                        .build();

        paymentRepository.save(payment);

        return saveProductOrder;
    }

    // 결제 검증
    public Boolean validatePayment(UUID userId, PaymentRequestDTO request) throws IamportResponseException, IOException {
        User user = userRepository.findById(userId).get();
        Payment payment = paymentRepository.findByMerchantUid(request.getMerchantUid()).get();

        // 이미 결제된 주문인지 확인
        if (PaymentStatus.PAID.equals(payment.getPaymentStatus())) {
            return false;
        }

        // Iamport API를 통해 결제 정보 조회
        com.siot.IamportRestClient.response.Payment paymentResponse = iamportClient.paymentByImpUid(request.getImpUid()).getResponse();

        // 주문번호 일치 확인
        if (!paymentResponse.getMerchantUid().equals(payment.getMerchantUid())) {
            return false;
        }

        String status = paymentResponse.getStatus();

        if ("PAID".equalsIgnoreCase(status)) {
            // 결제금액 확인
            if (paymentResponse.getAmount().compareTo(payment.getAmount()) != 0) {
                // 불일치 시 결제 취소
                CancelData cancelData = new CancelData(request.getImpUid(), true);
                iamportClient.cancelPaymentByImpUid(cancelData);

                payment.updatePaymentStatus(PaymentStatus.CANCELLED);
                paymentRepository.save(payment);

                return false;
            }
        } else {
            return false;
        }

        payment.updatePaymentStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        // 결제가 성공하면 포인트 증가
        user.addPoint(paymentResponse.getAmount().intValue());
        userRepository.save(user);

        return true;
    }
}
