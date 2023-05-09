package com.fashion.library.mapper;

import com.fashion.library.dto.ProductDto;
import com.fashion.library.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImage(dto.getImage());
        entity.set_deleted(dto.is_deleted());
        entity.set_activated(dto.is_activated());
        return entity;
    }
    public ProductDto toDto(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        dto.setPrice(entity.getPrice());
        dto.set_deleted(entity.is_deleted());
        dto.set_activated(entity.is_activated());
        return dto;
    }
}
