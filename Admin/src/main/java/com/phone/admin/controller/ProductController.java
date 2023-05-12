package com.phone.admin.controller;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.AdminDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.entity.BrandEntity;
import com.phone.library.entity.ColorEntity;
import com.phone.library.entity.MemoryEntity;
import com.phone.library.entity.StorageEntity;
import com.phone.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MemoryService memoryService;

    @GetMapping("products")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        // Get Admin
        String username = principal.getName();
        AdminDto adminDto = adminService.findByUserName(username);

        // Get All Product
        List<ProductDto> products = productService.findAll();
        // Get All category
        List<CategoryDto> categories = categoryService.findAllByActivated();
        // Get All brand
        List<BrandEntity> brands = brandService.findAll();
        // Get All color
        List<ColorEntity> colors = colorService.findAll();
        // Get All storage
        List<StorageEntity> storages = storageService.findAll();
        // Get All memory
        List<MemoryEntity> memories = memoryService.findAll();

        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BRANDS, brands);
        model.addAttribute(SystemConstants.COLORS, colors);
        model.addAttribute(SystemConstants.STORAGES, storages);
        model.addAttribute(SystemConstants.MEMORIES, memories);

        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.TITLE, "Product Manager");
        model.addAttribute(SystemConstants.PRODUCTS, products);
        model.addAttribute(SystemConstants.PRODUCT, new ProductDto());
        model.addAttribute(SystemConstants.SIZE, products.size());
        return "products";
    }

    @GetMapping("/products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        // Get Admin
        String username = principal.getName();
        AdminDto adminDto = adminService.findByUserName(username);
        /// Get All Product
        Page<ProductDto> products = productService.pageProducts(pageNo);
        // Get All category
        List<CategoryDto> categories = categoryService.findAllByActivated();
        // Get All brand
        List<BrandEntity> brands = brandService.findAll();
        // Get All color
        List<ColorEntity> colors = colorService.findAll();
        // Get All storage
        List<StorageEntity> storages = storageService.findAll();
        // Get All memory
        List<MemoryEntity> memories = memoryService.findAll();

        model.addAttribute(SystemConstants.TITLE, "Product Manager");

        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.BRANDS, brands);
        model.addAttribute(SystemConstants.COLORS, colors);
        model.addAttribute(SystemConstants.STORAGES, storages);
        model.addAttribute(SystemConstants.MEMORIES, memories);

        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.PRODUCTS, products);
        model.addAttribute(SystemConstants.PRODUCT, new ProductDto());
        model.addAttribute(SystemConstants.CATEGORIES, categories);
        model.addAttribute(SystemConstants.SIZE, products.getSize());
        model.addAttribute(SystemConstants.TOTAL_PAGE, products.getTotalPages());
        model.addAttribute(SystemConstants.CURRENT_PAGE, pageNo);
        return "products";
    }

    @GetMapping("/update-product")
    public String updateProduct(Model model, Principal principal, Long id) {
        if (principal == null) {
            return "redirect:/login";
        }
        // Get Admin
        String username = principal.getName();
        AdminDto adminDto = adminService.findByUserName(username);

        // Get All category
        List<CategoryDto> categories = categoryService.findAllByActivated();
        // Get All brand
        List<BrandEntity> brands = brandService.findAll();
        // Get All color
        List<ColorEntity> colors = colorService.findAll();
        // Get All storage
        List<StorageEntity> storages = storageService.findAll();
        // Get All memory
        List<MemoryEntity> memories = memoryService.findAll();

        ProductDto dto = productService.findById(id);
        System.out.println(dto);
        model.addAttribute(SystemConstants.PRODUCT, dto);
        model.addAttribute(SystemConstants.TITLE, "Update product");
        model.addAttribute(SystemConstants.BRANDS, brands);
        model.addAttribute(SystemConstants.COLORS, colors);
        model.addAttribute(SystemConstants.STORAGES, storages);
        model.addAttribute(SystemConstants.MEMORIES, memories);
        model.addAttribute(SystemConstants.ADMIN_DTO, adminDto);
        model.addAttribute(SystemConstants.CATEGORIES, categories);

        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdateProduct(Model model, @PathVariable("id") Long id, @RequestParam(value = "imageProduct") MultipartFile imageProduct, @ModelAttribute(SystemConstants.PRODUCT) ProductDto productDto, RedirectAttributes attributes) {
        try {
            productDto.setId(id);
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Update product successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/products/0";
    }


    @PostMapping("/add-product")
    public String addProduct(Model model, @RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes attributes, @ModelAttribute(SystemConstants.PRODUCT) ProductDto productDto, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Add product successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }

        return "redirect:/products/0";
    }

    @RequestMapping(value = "/enable-product", method = {RequestMethod.POST, RequestMethod.GET})
    public String enableProduct(Model model, RedirectAttributes attributes, Long id) {
        try {
            productService.enable(id);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Enabled product successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/delete-product", method = {RequestMethod.POST, RequestMethod.GET})
    public String disableProduct(Model model, RedirectAttributes attributes, Long id) {
        try {
            productService.delete(id);
            attributes.addFlashAttribute(SystemConstants.SUCCESS, "Deleted product successfully");
        } catch (Exception e) {
            attributes.addFlashAttribute(SystemConstants.FAIL, "The server has been errors");
            e.printStackTrace();
        }
        return "redirect:/products/0";
    }

    @RequestMapping(value = "/findProductById", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ProductDto findById(Long id) {
        return productService.findById(id);
    }


}
