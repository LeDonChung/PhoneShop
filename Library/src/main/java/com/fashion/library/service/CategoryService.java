package com.fashion.library.service;

import com.fashion.library.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto findByCode(String code);
    List<CategoryDto> findAll();
    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto);
    void delete(String code);
    void enable(String code);
}
