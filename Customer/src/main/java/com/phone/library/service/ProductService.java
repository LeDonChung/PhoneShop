package com.phone.library.service;

import com.phone.library.dto.CommentDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.entity.ProductEntity;
import com.phone.library.model.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAll();
    List<ProductEntity> findAllProduct();
    ProductDto findById(Long id);
    ProductDto save(MultipartFile image, ProductDto dto);
    ProductDto update(MultipartFile image, ProductDto dtoNew);
    void delete(Long id);
    void enable(Long id);

    Page<ProductDto> pageProducts(int pageNo);

    // Customer
    List<ProductDto> findFeaturedProduct();
    List<ProductDto> findOfferProducts();
    Page<ProductDto> getPageProducts(ProductFilter filter);
    List<ProductDto> findAlsoLike(String category);
    List<ProductDto> findBySearchKey(String key);
    ProductDto addComment(Long productId, CommentDto commentDto);
}
