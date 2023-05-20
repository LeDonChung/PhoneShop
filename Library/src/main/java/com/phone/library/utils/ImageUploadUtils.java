package com.phone.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUploadUtils {
    private final String UPLOAD_FOLDER = "D:\\SpringBoot\\FashionShop\\Admin\\src\\main\\resources\\static\\images\\img-product";
    private final String UPLOAD_FOLDER_LOGO = "D:\\SpringBoot\\FashionShop\\Admin\\src\\main\\resources\\static\\images\\logo-brand";

    public boolean uploadImage(MultipartFile imageProduct) {
        boolean isUpload = false;
        try {
            Files.copy(imageProduct.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separator, imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExisted(MultipartFile imageProduct) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + File.separator, imageProduct.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }
    public boolean uploadLogo(MultipartFile logoBrand) {
        boolean isUpload = false;
        try {
            Files.copy(logoBrand.getInputStream(), Paths.get(UPLOAD_FOLDER_LOGO + File.separator, logoBrand.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkLogoExisted(MultipartFile logoBrand) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER_LOGO + File.separator, logoBrand.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExisted;
    }
}
