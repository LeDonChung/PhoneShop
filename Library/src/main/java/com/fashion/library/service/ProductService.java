package com.fashion.library.service;

import com.fashion.library.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    ProductDto findById(Long id);
    ProductDto save(MultipartFile image, ProductDto dto);
    ProductDto update(MultipartFile image, ProductDto dtoNew);
    void delete(Long id);
    void enable(Long id);

    Page<ProductDto> pageProducts(int pageNo);

}
