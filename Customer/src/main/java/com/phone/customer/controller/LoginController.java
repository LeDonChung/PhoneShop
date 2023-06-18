package com.phone.customer.controller;

import com.phone.library.constants.Provider;
import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.model.ResetPassword;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import com.phone.library.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MailService mailService;

    @RequestMapping("/login")
    public String loginPage(Model model) {
        List<CategoryDto> categories = categoryService.findAllByActivated();
        model.addAttribute(SystemConstants.TITLE, "Login");
        model.addAttribute(SystemConstants.CUSTOMER, new CustomerDto());
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        List<CategoryDto> categories = categoryService.findAllByActivated();
        model.addAttribute(SystemConstants.CUSTOMER, new CustomerDto());
        model.addAttribute(SystemConstants.TITLE, "Register");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(Model model,
                                  @Valid @ModelAttribute(SystemConstants.CUSTOMER) CustomerDto customer,
                                  BindingResult result
    ) {
        // handler
        List<CategoryDto> categories = categoryService.findAllByActivated();
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.CUSTOMER, customer);
        try {
            // Check valid
            if (result.hasErrors()) {
                return "register";
            }

            String username = customer.getUsername();
            CustomerDto customerDto = customerService.findByUsername(username);
            if (customerDto != null) {
                // have account
                model.addAttribute(SystemConstants.MESSAGE, "Your email has been registered");
                return "register";
            }
            // check repeatPassword
            if (!customer.getPassword().equals(customer.getRepeatPassword())) {
                model.addAttribute(SystemConstants.MESSAGE, "Password is not same");
                return "register";
            }

            // register
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customerService.save(customer);
            model.addAttribute(SystemConstants.MESSAGE, "Register successfully");
            return "register";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(SystemConstants.MESSAGE, "The server has been errors");
            return "register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        List<CategoryDto> categories = categoryService.findAllByActivated();
        model.addAttribute(SystemConstants.TITLE, "Forgot Password");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        return "forgot-password";
    }

    @PostMapping("/reset-password")
    public String sendMailResetPassword(@RequestParam("email") String email, RedirectAttributes attributes, HttpServletRequest request) {
        try {
            if (email.isBlank()) {
                attributes.addFlashAttribute(SystemConstants.MESSAGE, "Please enter your email.");
                return "redirect:" + request.getHeader("Referer");
            }
            CustomerDto customer = customerService.findByUsernameAndProviderId(email, Provider.local.name());
            if (customer == null) {
                attributes.addFlashAttribute(SystemConstants.MESSAGE, "Email does not exist.");
                return "redirect:" + request.getHeader("Referer");
            }

            attributes.addFlashAttribute(SystemConstants.MESSAGE, "Please check your email.");
            mailService.sendEmailResetPassword(email);
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.MESSAGE, "The server has been errors.");
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/set-password")
    public String sendMailResetPassword(@RequestParam("email") String email, Model model) {
        model.addAttribute("yourEmail", email);
        model.addAttribute(SystemConstants.RESET_PASSWORD, new ResetPassword());
        return "set-password";
    }

    @GetMapping("/reset-password-new")
    public String showResetPassword(@Valid @ModelAttribute(SystemConstants.RESET_PASSWORD) ResetPassword resetPassword,
                                    BindingResult result,
                                    @RequestParam("email") String email,
                                    RedirectAttributes attributes,
                                    Model model) {

        try {
            if (result.hasErrors()) {
                model.addAttribute("yourEmail", email);
                model.addAttribute(SystemConstants.RESET_PASSWORD, resetPassword);
                return "set-password";
            }

            if (!resetPassword.getPassword().equals(resetPassword.getRepeatPassword())) {
                attributes.addFlashAttribute(SystemConstants.MESSAGE, "Password not same");
                return "redirect:/set-password?email=" + email;
            }

            // save password
            CustomerDto customerDto = customerService.findByUsernameAndProviderId(email, Provider.local.name());
            if (customerDto == null) {
                attributes.addFlashAttribute(SystemConstants.MESSAGE, "The server has been errors.");
            }

            customerService.changePassword(customerDto, passwordEncoder.encode(resetPassword.getPassword()));

        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute(SystemConstants.MESSAGE, "The server has been errors.");
            return "redirect:/set-password?email=" + email;
        }

        attributes.addFlashAttribute(SystemConstants.MESSAGE, "Reset password successfully. Login now!");
        return "redirect:/login";
    }
}
