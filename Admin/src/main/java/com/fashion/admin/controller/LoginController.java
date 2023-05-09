package com.fashion.admin.controller;

import com.fashion.library.constants.SystemConstants;
import com.fashion.library.dto.AdminDto;
import com.fashion.library.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute(SystemConstants.TITLE, "Login");
        model.addAttribute((SystemConstants.ADMIN_DTO), new AdminDto());
        return "login";
    }

    @RequestMapping(value = {"/index", "/"})
    public String home(Model model, Principal principal) {
        model.addAttribute(SystemConstants.TITLE, "Home page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        String username = principal.getName();
        AdminDto adminDto = adminService.findByUserName(username);

        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute(SystemConstants.TITLE, "Register");
        model.addAttribute(SystemConstants.ADMIN_DTO, new AdminDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerProcess(Model model, @Valid @ModelAttribute(SystemConstants.ADMIN_DTO) AdminDto adminDto, BindingResult result) {

        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        try {
            // Check valid
            if (result.hasErrors()) {
                return "register";
            }

            String username = adminDto.getUsername();
            AdminDto admin = adminService.findByUserName(username);
            if (admin != null) {
                // have account
                model.addAttribute(SystemConstants.MESSAGE, "Your username has been registered");
                return "register";
            }
            // check repeatPassword
            if (!adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                model.addAttribute(SystemConstants.MESSAGE, "Password is not same");
                return "register";
            }

            // register
            adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            adminService.save(adminDto);
            model.addAttribute(SystemConstants.MESSAGE, "Register successfully");
            return "register";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(SystemConstants.MESSAGE, "The server has been errors");
            return "register";
        }
    }
}
