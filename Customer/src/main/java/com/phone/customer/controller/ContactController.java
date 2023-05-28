package com.phone.customer.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.ContactDto;
import com.phone.library.service.CategoryService;
import com.phone.library.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ContactController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    public String showContact(Model model) {
        List<CategoryDto> categories = categoryService.findAllByActivated();

        model.addAttribute(SystemConstants.CONTACT_ACTIVE, "active");
        model.addAttribute(SystemConstants.CONTACT, new ContactDto());
        model.addAttribute(SystemConstants.TITLE, "Contact");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BREADCRUMB, "Contact");
        return "contact";
    }

    @PostMapping("/send-contact")
    public String sendContact(
            @Valid @ModelAttribute(SystemConstants.CONTACT) ContactDto contact,
            BindingResult result,
            Model model) {
        List<CategoryDto> categories = categoryService.findAllByActivated();
        model.addAttribute(SystemConstants.CONTACT_ACTIVE, "active");
        model.addAttribute(SystemConstants.CONTACT, contact);
        model.addAttribute(SystemConstants.TITLE, "Contact");
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BREADCRUMB, "Contact");

        try {
            if (result.hasErrors()) {
                return "contact";
            }

            // valid email and phone
            String regexEmail = "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$";
            if (!(contact.getEmail().matches(regexEmail))) {
                model.addAttribute(SystemConstants.MESSAGE, "Invalid email");
                return "contact";
            }

            String regexPhone = "^0(\\d){9}$";
            if (!(contact.getPhone().matches(regexPhone))) {
                model.addAttribute(SystemConstants.MESSAGE, "Invalid phone");
                return "contact";
            }

            contactService.save(contact);
            model.addAttribute(SystemConstants.MESSAGE, "Send message successfully");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute(SystemConstants.MESSAGE, "The server has been errors");
        }

        return "contact";
    }
}
