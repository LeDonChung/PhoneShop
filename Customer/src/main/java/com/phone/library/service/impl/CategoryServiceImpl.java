package com.phone.library.service.impl;

import com.phone.library.dto.CategoryDto;
import com.phone.library.entity.CategoryEntity;
import com.phone.library.mapper.CategoryMapper;
import com.phone.library.repository.CategoryRepository;
import com.phone.library.service.CategoryService;
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
        return convertListCategoryDto(entityList);
    }

    @Override
    public List<CategoryDto> findAllByActivated() {
        List<CategoryEntity> entityList = categoryRepository.findByActivated();

        return convertListCategoryDto(entityList);
    }

    private List<CategoryDto> convertListCategoryDto(List<CategoryEntity> entityList) {
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
        // find
        CategoryEntity entityOld = categoryRepository.findById(categoryDto.getId()).get();
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
