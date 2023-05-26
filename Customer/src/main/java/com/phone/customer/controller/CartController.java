package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.CartSession;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import com.phone.library.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CartController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartSession cartSession;

    @GetMapping("/cart")
    public String showCart(Model model, Principal principal, HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }

        // Get All Categories
        List<CategoryDto> categories = categoryService.findAllByActivated();

        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        model.addAttribute(SystemConstants.TITLE, "Cart");
        model.addAttribute(SystemConstants.BREADCRUMB, "Shopping Cart");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
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
    @RequestMapping(value = "/update-item-to-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateItemToCart(HttpServletRequest request,
                                @PathVariable("id") Long productId,
                                @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                                @RequestParam(value = "color") String color,
                                @RequestParam(value = "storage") String storage,
                                Principal principal,
                                HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }

        ProductDto productDto = productService.findById(productId);
        productDto.setColorCode(color);
        productDto.setStorageCode(storage);

        cartSession.updateItemToCart(session, productDto, quantity);

        return "redirect:" + request.getHeader("Referer");
    }
    @RequestMapping(value = "/remove-item-to-cart/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String removeItemToCart(HttpServletRequest request,
                                   @PathVariable("id") Long productId,
                                   @RequestParam(value = "color") String color,
                                   @RequestParam(value = "storage") String storage,
                                   Principal principal,
                                   HttpSession session) {

        if(principal == null) {
            return "redirect:/login";
        }


        ProductDto productDto = productService.findById(productId);
        productDto.setColorCode(color);
        productDto.setStorageCode(storage);

        cartSession.deleteToCart(session, productDto);

        return "redirect:" + request.getHeader("Referer");
    }
}
