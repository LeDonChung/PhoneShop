package com.phone.customer.controller;

import com.phone.library.dto.CustomerDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class CartController {

    public String showCart(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        CustomerDto customer =
    }
}
