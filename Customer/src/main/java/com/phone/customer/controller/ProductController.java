package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.*;
import com.phone.library.service.CategoryService;
import com.phone.library.service.MemoryService;
import com.phone.library.service.ProductService;
import com.phone.library.service.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/product/{id}")
    public String showProduct(Model model, @PathVariable("id") Long id, HttpSession session,
                              @RequestParam(value = "storageCode", required = false) String storageCode,
                              @RequestParam(value = "colorCode", required = false) String colorCode){
        try {
            List<CategoryDto> categories = categoryService.findAllByActivated();
            ProductDto productDto = productService.findById(id);
            List<StorageDto> storages = storeService.findStoragesByProductId(productDto.getId());
            if(storages.size() != 0) {
                List<ColorDto> colors = storeService.findColorsByStorageCodeAndProductId(storageCode == null ? storages.get(0).getCode() : storageCode, productDto.getId());
                String storageChoose = storageCode == null ? storages.get(0).getCode() : storageCode;
                model.addAttribute(SystemConstants.STORAGE_CHOOSE, storageChoose);
                String colorChoose = colorCode == null ? colors.get(0).getCode() : colorCode;
                model.addAttribute(SystemConstants.COLOR_CHOOSE, colorChoose);
                model.addAttribute(SystemConstants.STORAGES, storages);
                model.addAttribute(SystemConstants.COLORS, colors);
                model.addAttribute(SystemConstants.PRICE, storeService.getPriceByProductIdAndColorCodeAndStorageCode(id, colorChoose, storageChoose));

            } else {
                model.addAttribute(SystemConstants.ERROR, "This item is temporarily out of stock");
            }

            List<ProductDto> listAlsoLike = productService.findAlsoLike(productDto.getCategory().getCategoryCode());
            model.addAttribute(SystemConstants.PRODUCTS, listAlsoLike);
            model.addAttribute(SystemConstants.TITLE, "Product Detail");
            model.addAttribute(SystemConstants.PRODUCT, productDto);
            model.addAttribute(SystemConstants.CATEGORIES, categories);
            model.addAttribute(SystemConstants.BREADCRUMB, productDto.getCategory().getCategoryName());

        }catch(Exception e) {
            e.printStackTrace();
        }
        return "product-detail";
    }
}
