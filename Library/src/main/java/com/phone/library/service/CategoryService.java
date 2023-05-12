package com.phone.library.service;

import com.phone.library.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto findByCode(String code);
    List<CategoryDto> findAll();
    List<CategoryDto> findAllByActivated();
    CategoryDto save(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto);
    void delete(String code);
    void enable(String code);
}
