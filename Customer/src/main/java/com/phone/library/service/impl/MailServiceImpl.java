package com.phone.library.service.impl;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.service.MailService;
import com.phone.library.service.ThymeleafService;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;
    private final ThymeleafService thymeleafService;

    @Override
    public void createAccountSuccess(CustomerDto customerDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper hepper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            hepper.setTo(customerDto.getUsername());
            hepper.setSubject("Phone Shop Mail");

            // content
            Map<String, Object> variables = new HashMap<>();
            variables.put("last_name", customerDto.getLastName());
            variables.put("username", customerDto.getUsername());
            variables.put("first_name", customerDto.getFirstName());
            variables.put("date", new Date());

            hepper.setText(thymeleafService.createContent("send-email-register-success.html", variables), true);

            hepper.setFrom(email);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailOrderSuccess(OrderDto orderDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper hepper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            hepper.setTo(orderDto.getCustomer().getUsername());
            hepper.setSubject("Phone Shop Mail");

            // content
            Map<String, Object> variables = new HashMap<>();
            variables.put(SystemConstants.ORDERS, orderDto);
            variables.put("last_name", orderDto.getCustomer().getLastName());
            variables.put("first_name", orderDto.getCustomer().getFirstName());
            variables.put("email", orderDto.getCustomer().getUsername());
            variables.put("total_price", orderDto.getTotalPrice());
            variables.put("order_id", orderDto.getId());
            variables.put("order_date", orderDto.getOrderDate());
            variables.put("order_status", orderDto.getOrderStatus());
            variables.put("order_details", orderDto.getOrderDetails());
            variables.put("payment_method", orderDto.getPaymentMethod());
            variables.put("is_payment", orderDto.isPaymentStatus());

            hepper.setText(thymeleafService.createContent("send-email-order-success.html", variables), true);

            hepper.setFrom(email);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailResetPassword(String emailSender) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper hepper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            hepper.setSubject("Set Password");

            String content = String.format("<div>\n" +
                    "          <a href=\"http://phoneshop.azurewebsites.net/shop/set-password?email=%s\" target=\"_blank\">click link to set password</a>\n" +
                    "        </div>", emailSender);
            hepper.setText(content, true);

            hepper.setFrom(email);
            hepper.setTo(emailSender);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
