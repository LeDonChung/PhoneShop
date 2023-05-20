package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.ProductDto;
import com.phone.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product/{id}")
    public String showProduct(Model model, @PathVariable("id") Long id) {
        ProductDto productDto = productService.findById(id);
        List<ProductDto> listAlsoLike = productService.findAlsoLike(productDto.getCategory().getCategoryCode());
        model.addAttribute(SystemConstants.PRODUCTS, listAlsoLike);
        model.addAttribute(SystemConstants.TITLE, "Product Detail");
        model.addAttribute(SystemConstants.PRODUCT, productDto);
        model.addAttribute(SystemConstants.BREADCRUMB, productDto.getCategory().getCategoryName());

        return "product-detail";
    }
}
