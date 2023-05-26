package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.model.CartSession;
import com.phone.library.service.CustomerService;
import com.phone.library.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class CartController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartSession cartSession;

    @GetMapping("/cart")
    public String showCart(Model model, Principal principal, HttpSession session) {
        String username = principal.getName();
        CustomerDto customer = customerService.findByUsername(username);
        if(customer == null) {
            return "redirect:/login";
        }

        return "cart";
    }

    @RequestMapping(value = "/add-to-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String addItemToCart(HttpServletRequest request,
                                @PathVariable("id") Long productId,
                                @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                Principal principal,
                                HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }

        String colorCode = (String) session.getAttribute(SystemConstants.COLOR_CHOOSE);
        String storageCode = (String) session.getAttribute(SystemConstants.STORAGE_CHOOSE);

        if(colorCode == null || storageCode == null) {
            return "redirect:/product/" + productId;
        }

        ProductDto productDto = productService.findById(productId);
        productDto.setColorCode(colorCode);
        productDto.setStorageCode(storageCode);

        cartSession.addItemToCart(session, productDto, quantity);

        return "redirect:" + request.getHeader("Referer");
    }
}
