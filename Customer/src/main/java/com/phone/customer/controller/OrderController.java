package com.phone.customer.controller;

import com.phone.library.constants.PaymentMethod;
import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.CartSession;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import com.phone.library.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Set;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartSession cartSession;

    @PostMapping(value = "/order/{id}")
    @ResponseBody
    public OrderDto updateStatusOrder(
            @PathVariable("id") Long id,
            @RequestParam("status") String status) {
        return orderService.updateStatus(id, status);
    }

    @GetMapping("/order/create-order")
    public String saveOrder(Principal principal,
                            @RequestParam(value = "payment", defaultValue = "paypal") String payment,
                            RedirectAttributes attributes,
                            HttpSession session) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        OrderDto order = (OrderDto) session.getAttribute(SystemConstants.ORDER);

        CustomerDto customerDto = customerService.findByUsername(principal.getName());
        customerDto.setAddress(order.getAddress());
        customerDto.setPhone(order.getPhoneNumber());

        OrderDto dto = orderService.saveOrder(cart, cartItems, customerDto, payment, order.getNotes());
        if(dto == null) {
            attributes.addFlashAttribute(SystemConstants.ORDER_FAIL, "fail");
            return "redirect:/account/order-history";
        }
        cartSession.removeAll(session);
        attributes.addFlashAttribute(SystemConstants.ORDER_SUCCESS, "success");
        return "redirect:/account/order-history";
    }
}
