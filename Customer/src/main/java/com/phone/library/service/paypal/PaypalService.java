package com.phone.library.service.paypal;

import com.paypal.api.payments.Payment;

public interface PaypalService {
    Payment createPayment(Double total,
                          String currency,
                          String method,
                          String intent,
                          String description,
                          String cancelUrl,
                          String successUrl);

    Payment executePayment(String paymentId, String payerId);
}
