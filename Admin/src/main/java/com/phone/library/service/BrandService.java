package com.phone.library.service;

import com.phone.library.dto.BrandDto;
import com.phone.library.dto.CategoryDto;
import com.phone.library.entity.BrandEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandService {
    // Admin
    List<BrandDto> findAll();
    BrandDto save(BrandDto brandDto, MultipartFile logoBrand);
    BrandDto update(BrandDto brandDto, MultipartFile logoBrand);
    void delete(String code);
    void enable(String code);

    // Customer
    List<BrandDto> findAllBrandAndProduct();
    BrandDto findByCode(String code);
}
