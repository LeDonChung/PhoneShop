package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.*;
import com.phone.library.entity.ProductEntity;
import com.phone.library.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/product")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public String showProduct(Model model, @PathVariable("id") Long id, HttpSession session,
                              @RequestParam(value = "storageCode", required = false) String storageCode,
                              @RequestParam(value = "colorCode", required = false) String colorCode,
                              Principal principal){
        try {
            List<CategoryDto> categories = categoryService.findAllByActivated();
            ProductDto productDto = productService.findById(id);
            List<StorageDto> storages = storeService.findStoragesByProductId(productDto.getId());
            if(storages.size() != 0) {
                List<ColorDto> colors = storeService.findColorsByStorageCodeAndProductId(storageCode == null ? storages.get(0).getCode() : storageCode, productDto.getId());
                String storageChoose = storageCode == null ? storages.get(0).getCode() : storageCode;
                session.setAttribute(SystemConstants.STORAGE_CHOOSE, storageChoose);
                String colorChoose = colorCode == null ? colors.get(0).getCode() : colorCode;
                session.setAttribute(SystemConstants.COLOR_CHOOSE, colorChoose);
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
            model.addAttribute(SystemConstants.SHOP_DETAIL_ACTIVE, "active");

            if(principal != null) {
                CustomerDto customerDto = customerService.findByUsername(principal.getName());
                List<ProductEntity> favorites = customerDto.getFavorites();
                ProductEntity productEntity = new ProductEntity();
                productEntity.setId(id);
                if(favorites.contains(productEntity)) {
                    model.addAttribute("isFavorite", "true");
                }
                model.addAttribute(SystemConstants.FAVORITE_SIZE, customerDto.getFavorites().size());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return "product-detail";
    }

    @PostMapping("/{id}/comment")
    @ResponseBody
    public ProductDto addComment(@PathVariable("id") Long productId,
                                 @RequestBody CommentDto commentDto) {
        ProductDto productDto = productService.addComment(productId, commentDto);
        System.out.println(productDto);
        return productDto;
    }
}
