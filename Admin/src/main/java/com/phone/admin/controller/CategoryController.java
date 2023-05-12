package com.phone.admin.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.service.AdminService;
import com.phone.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdminService adminService;

    @GetMapping("categories")
    public String showCategoryManager(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        // Get Admin
        String username = principal.getName();
        AdminDto adminDto = adminService.findByUserName(username);

        // Get All Category
        List<CategoryDto> categories = categoryService.findAll();
        // Get Size
        int size = categories.size();

        // Add Attribute
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.SIZE, size);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.CATEGORY, new CategoryDto());
        return "categories";
    }

    @PostMapping("/add-category")
    public String addCategory(Model model, @ModelAttribute(SystemConstants.CATEGORY) CategoryDto categoryDto, RedirectAttributes attributes) {
        try {
            categoryService.save(categoryDto);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Add category successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/categories";
    }
    @GetMapping("/update-category")
    public String updateCategory(Model model, CategoryDto categoryDto, RedirectAttributes attributes) {
        try {
            categoryService.update(categoryDto);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Update category successfully");
        } catch(Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.GET, RequestMethod.POST})
    public String enableCategory(Model model, String code, RedirectAttributes attributes) {
        try {
            categoryService.enable(code);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Enable successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.GET, RequestMethod.POST})
    public String disableCategory(Model model, String code, RedirectAttributes attributes) {
        try {
            categoryService.delete(code);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Delete successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/categories";
    }

    @RequestMapping(value = "/findCategoryByCode", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public CategoryDto findByCode(String code) {
        return categoryService.findByCode(code);
    }
}
