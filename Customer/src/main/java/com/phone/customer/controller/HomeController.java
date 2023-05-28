package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.BrandDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model, HttpSession session, Principal principal) {
        if (session.getAttribute(SystemConstants.STORAGE_CHOOSE) != null) {
            session.removeAttribute(SystemConstants.STORAGE_CHOOSE);
        }
        if (session.getAttribute(SystemConstants.COLOR_CHOOSE) != null) {
            session.removeAttribute(SystemConstants.COLOR_CHOOSE);
        }
        if (principal != null) {
            CustomerDto customer = customerService.findByUsername(principal.getName());
            model.addAttribute(SystemConstants.FAVORITE_SIZE, customer.getFavorites().size());
            session.setAttribute(SystemConstants.USER_LOGIN, customer);
        }

        List<CategoryDto> categories = categoryService.findAllByActivated();
        List<BrandDto> brands = brandService.findAllBrandAndProduct();
        List<ProductDto> featuredProducts = productService.findFeaturedProduct();
        List<ProductDto> offerProducts = productService.findOfferProducts();

        model.addAttribute(SystemConstants.TITLE, "Home");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BRANDS, brands);
        model.addAttribute(SystemConstants.HOME_ACTIVE, "active");
        model.addAttribute("featuredProducts", featuredProducts);
        model.addAttribute("offerProducts", offerProducts);
        return "index";
    }

}
