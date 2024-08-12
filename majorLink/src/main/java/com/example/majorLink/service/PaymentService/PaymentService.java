package com.example.majorLink.service.PaymentService;

import com.example.majorLink.domain.ProductOrder;
import com.example.majorLink.dto.request.PaymentRequestDTO;
import com.example.majorLink.dto.request.ProductOrderRequestDTO;
import com.siot.IamportRestClient.exception.IamportResponseException;

import java.io.IOException;
import java.util.UUID;

public interface PaymentService {

    ProductOrder prepareProductOrder(UUID userId, ProductOrderRequestDTO request) throws IamportResponseException, IOException;
    Boolean validatePayment(PaymentRequestDTO request) throws IamportResponseException, IOException;

}
