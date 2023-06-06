package com.phone.admin.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.service.AdminService;
import com.phone.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/orders")
    public String showOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        AdminDto adminDto = adminService.findByUserName(principal.getName());

        List<OrderDto> orders = orderService.findAll();
        model.addAttribute(SystemConstants.ORDERS, orders);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);

        return "orders";
    }
    @GetMapping("orders/{id}")
    public String showDetail(Model model,
                             Principal principal,
                             @PathVariable("id") long id) {
        if (principal == null) {
            return "redirect:/login";
        }

        AdminDto adminDto = adminService.findByUserName(principal.getName());
        OrderDto order = orderService.findById(id);
        model.addAttribute(SystemConstants.ORDER, order);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);

        return "order-details";
    }

    @GetMapping(value = "/change-status-order/{id}")
    public String updateStatusOrder(
            @PathVariable("id") Long id,
            @RequestParam("status") String status,
            Principal principal,
            RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            orderService.updateStatus(id, status);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Change status successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
        }
        return "redirect:/orders";
    }
}
