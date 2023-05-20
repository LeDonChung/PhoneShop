package com.phone.admin.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.BrandDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.service.AdminService;
import com.phone.library.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private AdminService adminService;
    @GetMapping("/brands")
    public String showBrandManager(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        // Get Admin
        String username = principal.getName();
        AdminDto adminDto = adminService.findByUserName(username);

        // Get All Brand
        List<BrandDto> brands = brandService.findAll();

        // Get Size
        int size = brands.size();

        // Add Attribute
        model.addAttribute(SystemConstants.BRANDS, brands);
        model.addAttribute(SystemConstants.SIZE, size);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.BRAND, new BrandDto());
        return "brands";
    }
    @PostMapping("/add-brand")
    public String addBrand(@ModelAttribute(SystemConstants.BRAND) BrandDto brandDto, RedirectAttributes attributes,  @RequestParam(value = "logoBrand") MultipartFile logoBrand) {
        try {
            brandService.save(brandDto, logoBrand);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Add brand successfully");
        } catch(Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.ERROR, "The server has been errors");
        }
        return "redirect:/brands";
    }
    @RequestMapping(value = "/enable-brand", method = {RequestMethod.GET, RequestMethod.POST})
    public String enableBrand(@RequestParam("code") String code, RedirectAttributes attributes) {
        try {
            brandService.enable(code);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Enable brand successfully");
        } catch(Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.ERROR, "The server has been errors");
        }
        return "redirect:/brands";
    }
    @RequestMapping(value = "/delete-brand", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteBrand(@RequestParam("code") String code, RedirectAttributes attributes) {
        try {
            brandService.delete(code);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Delete brand successfully ");
        } catch(Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.ERROR, "The server has been errors");
        }
        return "redirect:/brands";
    }
    @RequestMapping(value = "/findBrandByCode")
    @ResponseBody
    public BrandDto findByCode(@RequestParam("code") String code) {
        return brandService.findByCode(code);
    }

    @PostMapping("/update-brand")
    public String processUpdateBrand(@RequestParam(value = "logoBrand", required = true)  MultipartFile logoBrand,
                                     @RequestParam("id") Long id,
                                     BrandDto brandDto,
                                     RedirectAttributes attributes) {
        try {
            brandDto.setId(id);
            brandService.update(brandDto, logoBrand);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "update brand successfully ");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.ERROR, "The server has been errors");
        }
        return "redirect:/brands";
    }
}
