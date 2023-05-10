package com.fashion.library.service.impl;

import com.fashion.library.dto.CategoryDto;
import com.fashion.library.entity.CategoryEntity;
import com.fashion.library.mapper.CategoryMapper;
import com.fashion.library.repository.CategoryRepository;
import com.fashion.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryDto findByCode(String code) {
        CategoryEntity entity = categoryRepository.findByCategoryCode(code);
        return categoryMapper.toDto(entity);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryEntity> entityList = categoryRepository.findAll();
        if (entityList == null) {
            entityList = new ArrayList<>();
        }

        List<CategoryDto> dtoList = new ArrayList<>();
        for (CategoryEntity entity : entityList) {
            dtoList.add(categoryMapper.toDto(entity));
        }

        return dtoList;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {

        // config
        CategoryEntity entity = categoryMapper.toEntity(categoryDto);
        entity.set_deleted(false);
        entity.set_activated(true);

        // save
        CategoryEntity entityNew = categoryRepository.save(entity);

        // return
        categoryDto = categoryMapper.toDto(entityNew);
        return categoryDto;
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        System.out.println(categoryDto);
        // find
        CategoryEntity entityOld = categoryRepository.findByCategoryCode(categoryDto.getCategoryCode());
        System.out.println(entityOld);
        // config
        CategoryEntity entityNew = categoryMapper.toEntity(entityOld, categoryDto);

        //return
        return categoryMapper.toDto(categoryRepository.save(entityNew));
    }

    @Override
    public void delete(String code) {
        CategoryEntity entity = categoryRepository.findByCategoryCode(code);
        entity.set_deleted(true);
        entity.set_activated(false);
        categoryRepository.save(entity);
    }

    @Override
    public void enable(String code) {
        CategoryEntity entity = categoryRepository.findByCategoryCode(code);
        entity.set_deleted(false);
        entity.set_activated(true);
        categoryRepository.save(entity);
    }
}