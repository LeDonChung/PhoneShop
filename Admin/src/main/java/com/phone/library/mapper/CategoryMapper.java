package com.phone.library.mapper;


import com.phone.library.dto.CategoryDto;
import com.phone.library.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto toDto(CategoryEntity entity) {
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setCategoryCode(entity.getCategoryCode());
        dto.setCategoryName(entity.getCategoryName());
        dto.setDescription(entity.getDescription());
        dto.set_deleted(entity.is_deleted());
        dto.set_activated(entity.is_activated());
        return dto;
    }

    public CategoryEntity toEntity(CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryCode(dto.getCategoryCode());
        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public CategoryEntity toEntity(CategoryEntity entity, CategoryDto dto) {
        entity.setCategoryCode(dto.getCategoryCode());
        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
