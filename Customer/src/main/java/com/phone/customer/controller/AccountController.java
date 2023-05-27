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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @GetMapping("/order-history")
    public String showMyOrder(Principal principal, Model model) {
        if(principal  == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.findByUsername(principal.getName());
            model.addAttribute(SystemConstants.FAVORITE_SIZE, customer.getFavorites().size());
        }

        List<CategoryDto> categories = categoryService.findAllByActivated();
        CustomerDto customer = customerService.findByUsername(principal.getName());
        List<OrderDto> orders = orderService.findByCustomerId(customer.getId());
        model.addAttribute(SystemConstants.CUSTOMER, customer);
        model.addAttribute(SystemConstants.ORDERS, orders);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.ORDER_HISTORY_ACTIVE, "active");
        model.addAttribute(SystemConstants.TITLE, "Orders");
        return "my-order";
    }
    @GetMapping("/favorites")
    public String showFavorites(Principal principal, Model model) {
        if(principal  == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.findByUsername(principal.getName());
            model.addAttribute(SystemConstants.FAVORITE_SIZE, customer.getFavorites().size());
        }
        List<CategoryDto> categories = categoryService.findAllByActivated();

        CustomerDto customer = customerService.findByUsername(principal.getName());
        List<ProductEntity> favorites = customer.getFavorites();

        model.addAttribute(SystemConstants.FAVORITES, favorites);
        model.addAttribute(SystemConstants.CUSTOMER, customer);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.FAVORITE_ACTIVE, "active");
        model.addAttribute(SystemConstants.TITLE, "Favorites");
        return "my-favorite";
    }
    @RequestMapping(value = "/remove-favorite/{productId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String removeFavorite(Principal principal,
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
    public String addFavorite(Principal principal,
                              @PathVariable Long productId,
                              HttpServletRequest request) {
        if(principal  == null) {
            return "redirect:/login";
        }
        CustomerDto customerDto = customerService.findByUsername(principal.getName());
        customerService.addFavorite(productId, customerDto);
        return "redirect:" + request.getHeader("Referer");
    }
    @GetMapping("/change-password")
    public String showChangePassword(Model model,
                                     Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.findByUsername(principal.getName());
            model.addAttribute(SystemConstants.FAVORITE_SIZE, customer.getFavorites().size());
        }
        CustomerDto customer = customerService.findByUsername(principal.getName());
        List<CategoryDto> categories = categoryService.findAllByActivated();
        model.addAttribute(SystemConstants.CUSTOMER, customer);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.CHANGE_PASSWORD_ACTIVE, "active");
        model.addAttribute(SystemConstants.TITLE, "Change Password");
        return "change-password";
    }

    @PostMapping("/do-change-password")
    public String processChangePassword(Principal principal,
                                        RedirectAttributes attributes,
                                        @RequestParam("currentPassword") String currentPassword,
                                        @RequestParam("newPassword") String newPassword) {
        if(principal == null) {
            return "redirect:/login";
        }

        CustomerDto customer = customerService.findByUsername(principal.getName());
        if(!(passwordEncoder.matches(currentPassword, customer.getPassword()))) {
            attributes.addFlashAttribute(SystemConstants.MESSAGE, "The current password and old password must match.");
            return "redirect:/account/change-password";
        }

        customerService.changePassword(customer, passwordEncoder.encode(newPassword));
        attributes.addFlashAttribute(SystemConstants.MESSAGE, "Change password successfully.");
        return "redirect:/account/change-password";
    }


}
