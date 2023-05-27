package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CheckoutController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/checkout")
    public String showCheckout(Model model,
                               Principal principal,
                               HttpSession session
                               ) {
        if(principal == null ){
            return "redirect:/login";
        }

        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null || cartItems == null) {
            return "redirect:/cart";
        }

        if(cartItems.size() == 0) {
            return "redirect:/cart";
        }
        CustomerDto customerDto = customerService.findByUsername(principal.getName());
        List<CategoryDto> categories = categoryService.findAllByActivated();

        model.addAttribute(SystemConstants.CUSTOMER, customerDto);
        model.addAttribute(SystemConstants.TITLE, "Cart");
        model.addAttribute(SystemConstants.BREADCRUMB, "Checkout");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        return "checkout";
    }
}
