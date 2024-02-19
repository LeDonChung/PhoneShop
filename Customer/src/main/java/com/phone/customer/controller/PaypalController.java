package com.phone.customer.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Order;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.phone.library.constants.PaymentMethod;
import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.CartSession;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.service.CustomerService;
import com.phone.library.service.OrderService;
import com.phone.library.service.paypal.PaypalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class PaypalController {
    private final PaypalService service;
    public final String SUCCESS_URL = "paypal/success";
    public final String CANCEL_URL = "paypal/cancel";

    public final CustomerService customerService;

    public final OrderService orderService;

    @GetMapping("/paypal")
    public String payment(Principal principal,
                          Model model,
                          HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);

        OrderDto order = (OrderDto) model.getAttribute(SystemConstants.ORDER);
        session.setAttribute(SystemConstants.ORDER, order);

        Payment payment = service.createPayment(cart.getTotalPrice(), "USD", PaymentMethod.paypal.name(),
                "sale", order.getNotes(), "http://localhost:8021/shop/" + CANCEL_URL,
                "http://localhost:8021/shop/" + SUCCESS_URL);
        for(Links link:payment.getLinks()) {
            if(link.getRel().equals("approval_url")) {
                return "redirect:"+link.getHref();
            }
        }

        return "redirect:/order-history";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "redirect:/home";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        Payment payment = service.executePayment(paymentId, payerId);
        if (payment.getState().equals("approved")) {
            return "redirect:/order/create-order";
        }

        return "redirect:/home";
    }
}
