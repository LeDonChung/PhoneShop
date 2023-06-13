package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.BrandDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.model.ProductFilter;
import com.phone.library.service.BrandService;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import com.phone.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class SearchController {

    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping("/search")
    public String searching(@RequestParam("key") String key, Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            CustomerDto customer = customerService.findByUsername(principal.getName());
            model.addAttribute(SystemConstants.FAVORITE_SIZE, customer.getFavorites().size());
        }
        if(session.getAttribute(SystemConstants.STORAGE_CHOOSE) != null) {
            session.removeAttribute(SystemConstants.STORAGE_CHOOSE);
        }
        if(session.getAttribute(SystemConstants.COLOR_CHOOSE) != null) {
            session.removeAttribute(SystemConstants.COLOR_CHOOSE);
        }

        /// Get All Products
        List<ProductDto> products = productService.findBySearchKey(key);

        model.addAttribute(SystemConstants.PRODUCTS, products);
        model.addAttribute(SystemConstants.BREADCRUMB, "Products");
        model.addAttribute(SystemConstants.TITLE, "Shop");
        model.addAttribute(SystemConstants.SHOP_ACTIVE, "active");
        return "product-search";
    }
}
