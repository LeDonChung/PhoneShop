package com.phone.customer.controller;

import com.phone.library.dto.OrderDto;
import com.phone.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/order/{id}")
    public OrderDto updateStatusOrder(
            @PathVariable("id") Long id,
            @RequestParam("status") String status) {
        return orderService.updateStatus(id, status);
    }
}
