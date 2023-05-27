package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.entity.ProductEntity;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import com.phone.library.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/order-history")
    public String showMyOrder(Principal principal, Model model) {
        if(principal  == null) {
            return "redirect:/login";
        }
        List<CategoryDto> categories = categoryService.findAllByActivated();
        CustomerDto customer = customerService.findByUsername(principal.getName());
        List<OrderDto> orders = orderService.findByCustomerId(customer.getId());
        model.addAttribute(SystemConstants.CUSTOMER, customer);
        model.addAttribute(SystemConstants.ORDERS, orders);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.ORDER_HISTORY_ACTIVE, "active");
        return "my-order";
    }
    @GetMapping("/favorites")
    public String showFavorites(Principal principal, Model model) {
        if(principal  == null) {
            return "redirect:/login";
        }
        List<CategoryDto> categories = categoryService.findAllByActivated();

        CustomerDto customer = customerService.findByUsername(principal.getName());
        List<ProductEntity> favorites = customer.getFavorites();

        model.addAttribute(SystemConstants.FAVORITES, favorites);
        model.addAttribute(SystemConstants.CUSTOMER, customer);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.FAVORITE_ACTIVE, "active");
        return "my-favorite";
    }
    @RequestMapping(value = "/remove-favorite/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String removeFavorite(Principal principal,
                                 Model model,
                                 @PathVariable Long productId,
                                 HttpServletRequest request) {
        if(principal  == null) {
            return "redirect:/login";
        }
        CustomerDto customerDto = customerService.findByUsername(principal.getName());
        customerService.removeFavorite(productId, customerDto);
        return "redirect:" + request.getHeader("Referer");
    }
    @RequestMapping(value = "/add-favorite/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String addFavorite(Principal principal, Model model,
                              @PathVariable Long productId,
                              HttpServletRequest request) {
        if(principal  == null) {
            return "redirect:/login";
        }
        CustomerDto customerDto = customerService.findByUsername(principal.getName());
        customerService.addFavorite(productId, customerDto);
        return "redirect:" + request.getHeader("Referer");
    }
}
