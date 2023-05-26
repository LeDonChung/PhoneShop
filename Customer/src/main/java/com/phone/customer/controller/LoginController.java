package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.service.CategoryService;
import com.phone.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
                model.addAttribute(SystemConstants.MESSAGE, "Your username has been registered");
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

}
