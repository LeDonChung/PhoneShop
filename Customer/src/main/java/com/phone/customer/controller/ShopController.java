package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.BrandDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.entity.BrandEntity;
import com.phone.library.model.ProductFilter;
import com.phone.library.service.BrandService;
import com.phone.library.service.CategoryService;
import com.phone.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @GetMapping("/products")
    public String shopPage(Model model,
                           @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                           @RequestParam(value = "category", required = false) String category,
                           @RequestParam(value = "brand", required = false) String brand,
                           @RequestParam(value = "sortBy", required = false) String sortBy,
                           @RequestParam(value = "sortType", required = false) String sortType,
                           HttpSession session) {

        if(session.getAttribute(SystemConstants.STORAGE_CHOOSE) != null) {
            session.removeAttribute(SystemConstants.STORAGE_CHOOSE);
        }
        if(session.getAttribute(SystemConstants.COLOR_CHOOSE) != null) {
            session.removeAttribute(SystemConstants.COLOR_CHOOSE);
        }
        ProductFilter filter = (ProductFilter) session.getAttribute(SystemConstants.PRODUCT_FILTER);
        if (filter == null) {
            filter = new ProductFilter();
        } else {
            filter = new ProductFilter(pageNo, category, brand, sortBy, sortType);
        }

        /// Get All Products
        Page<ProductDto> products = productService.getPageProducts(filter);
        // Get All Categories
        List<CategoryDto> categories = categoryService.findAllByActivated();
        // Get All Brands
        List<BrandDto> brands = brandService.findAll();

        model.addAttribute(SystemConstants.PRODUCTS, products);
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BRANDS, brands);
        model.addAttribute(SystemConstants.CURRENT_PAGE, pageNo);
        model.addAttribute(SystemConstants.TOTAL_PAGE, products.getTotalPages());
        model.addAttribute(SystemConstants.BREADCRUMB, "Products");
        model.addAttribute(SystemConstants.TITLE, "Shop");

        session.setAttribute(SystemConstants.PRODUCT_FILTER, filter);

        return "shop";
    }
}
