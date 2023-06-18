package com.phone.library.service;

import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;

public interface MailService {
    void createAccountSuccess(CustomerDto customerDto);
    void sendEmailOrderSuccess(OrderDto orderDto);
    void sendEmailResetPassword(String email);
}
