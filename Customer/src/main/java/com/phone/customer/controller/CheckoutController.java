package com.phone.customer.controller;

import com.phone.library.constants.PaymentMethod;
import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.entity.OrderEntity;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.CartSession;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import com.phone.library.service.MailService;
import com.phone.library.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartSession cartSession;
    @Autowired
    private MailService mailService;


    @GetMapping("/checkout")
    public String showCheckout(Model model,
                               Principal principal,
                               HttpSession session
                               ) {
        if(principal == null ){
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.findByUsername(principal.getName());
            model.addAttribute(SystemConstants.FAVORITE_SIZE, customer.getFavorites().size());
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
        model.addAttribute(SystemConstants.ORDER, new OrderDto());
        model.addAttribute(SystemConstants.PAGES_ACTIVE, "active");

        return "checkout";
    }
    @PostMapping("/do-checkout")
    public String saveOrder(Principal principal,
                            @ModelAttribute(SystemConstants.ORDER) OrderDto order,
                            @RequestParam("payment") String payment,
                            RedirectAttributes attributes,
                            HttpSession session) {
        if (principal == null) {
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

        if(payment.equals(PaymentMethod.paypal.name())) {
            attributes.addFlashAttribute(SystemConstants.ORDER, order);
            return "redirect:/paypal";
        }
        System.out.println(payment);

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
