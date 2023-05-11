package com.fashion.library.mapper;

import com.fashion.library.dto.ProductDto;
import com.fashion.library.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setCostPrice(dto.getCostPrice());
        entity.setSalePrice(dto.getSalePrice());
        entity.setQuantity(dto.getQuantity());
        entity.set_deleted(dto.is_deleted());
        entity.set_activated(dto.is_activated());
        return entity;
    }

    public ProductEntity toEntity(ProductEntity entityNew, ProductDto dto) {
        entityNew.setProductName(dto.getProductName());
        entityNew.setDescription(dto.getDescription());
        entityNew.setCostPrice(dto.getCostPrice());
        entityNew.setSalePrice(dto.getSalePrice());
        entityNew.setQuantity(dto.getQuantity());
        return entityNew;
    }

    public ProductDto toDto(ProductEntity entity) {
        ProductDto dto = new ProductDto();
        dto.setId(entity.getId());
        dto.setProductName(entity.getProductName());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        dto.setCostPrice(entity.getCostPrice());
        dto.setSalePrice(entity.getSalePrice());
        dto.setQuantity(entity.getQuantity());
        dto.set_deleted(entity.is_deleted());
        dto.set_activated(entity.is_activated());
        return dto;
    }
}
