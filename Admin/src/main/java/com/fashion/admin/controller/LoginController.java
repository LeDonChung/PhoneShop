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

    /*
    @PostMapping("/do-login")
    public String loginProcess(Model model, @ModelAttribute(SystemConstants.ADMIN_DTO) AdminDto adminDto) {
        model.addAttribute((SystemConstants.ADMIN_DTO), adminDto);
        if(adminDto.getUsername() == null || adminDto.getPassword() == null) {
            System.out.println("1. " + adminDto.getUsername());
            System.out.println("1. " + adminDto.getPassword());
            return "redirect:/login?error";
        }

        AdminDto admin = adminService.findByUserName(adminDto.getUsername());
        if(admin == null) {
            System.out.println("2. " + adminDto.getUsername());
            System.out.println("2. " + adminDto.getPassword());
            return "redirect:/login?error";
        }

        if(!passwordEncoder.encode(adminDto.getPassword()).equals(admin.getPassword())) {
            System.out.println("3. " + adminDto.getUsername());
            System.out.println("3. " + adminDto.getPassword());
            System.out.println("4. " + admin.getUsername());
            System.out.println("4. " + admin.getPassword());
            return "redirect:/login?error";
        }

        return "redirect:/index";
    }
    */

    @RequestMapping("/index")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
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
