package com.phone.admin.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.ColorDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.dto.StoreDto;
import com.phone.library.entity.ColorEntity;
import com.phone.library.entity.ProductEntity;
import com.phone.library.entity.StorageEntity;
import com.phone.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/stores")
    public String showStoreManager(Principal principal,
                                   Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        AdminDto adminDto = adminService.findByUserName(principal.getName());

        List<StoreDto> stores = storeService.findAll();
        List<ColorEntity> colors = colorService.findAll();
        List<StorageEntity> storages = storageService.findAll();
        List<ProductEntity> products = productService.findAllProduct();

        model.addAttribute(SystemConstants.COLORS, colors);
        model.addAttribute(SystemConstants.STORAGES, storages);
        model.addAttribute(SystemConstants.PRODUCTS, products);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.STORES, stores);
        model.addAttribute(SystemConstants.STORE, new StoreDto());

        return "stores";
    }

    @PostMapping("/add-store")
    public String addNewStore(@ModelAttribute(SystemConstants.STORE) StoreDto store,
                              RedirectAttributes attributes,
                              Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        try {
            storeService.save(store);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Add store success.");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.ERROR, "The server has been errors.");
        }
        return "redirect:/stores";
    }

    @DeleteMapping(value = "/stores/{id}")
    public ResponseEntity<Long> deleteStore(@PathVariable Long id) {
        storeService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
