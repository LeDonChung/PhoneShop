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
        if (dto.getCategory() != null) {
            entity.setCategory(dto.getCategory());
        }

        if (dto.getBrand() != null) {
            entity.setBrand(dto.getBrand());
        }

        if (dto.getColors().size() != 0) {
            entity.setColors(dto.getColors());
        }

        if (dto.getMemories().size() != 0) {
            entity.setMemories(dto.getMemories());
        }

        if (dto.getStorages().size() != 0) {
            entity.setStorages(dto.getStorages());
        }

        return entity;
    }

    public ProductEntity toEntity(ProductEntity entityNew, ProductDto dto) {
        entityNew.setProductName(dto.getProductName());
        entityNew.setDescription(dto.getDescription());
        entityNew.setCostPrice(dto.getCostPrice());
        entityNew.setSalePrice(dto.getSalePrice());
        entityNew.setQuantity(dto.getQuantity());
        if (dto.getCategory() != null) {
            entityNew.setCategory(dto.getCategory());
        }
        if (dto.getBrand() != null) {
            entityNew.setBrand(dto.getBrand());
        }

        if (dto.getColors().size() != 0) {
            entityNew.setColors(dto.getColors());
        }

        if (dto.getMemories().size() != 0) {
            entityNew.setMemories(dto.getMemories());
        }

        if (dto.getStorages().size() != 0) {
            entityNew.setStorages(dto.getStorages());
        }
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

        if (entity.getCategory() != null) {
            dto.setCategory(entity.getCategory());
        }
        if (entity.getBrand() != null) {
            dto.setBrand(entity.getBrand());
        }

        if (entity.getColors().size() != 0) {
            dto.setColors(entity.getColors());
        }

        if (entity.getMemories().size() != 0) {
            dto.setMemories(entity.getMemories());
        }

        if (entity.getStorages().size() != 0) {
            dto.setStorages(entity.getStorages());
        }
        return dto;
    }
}
