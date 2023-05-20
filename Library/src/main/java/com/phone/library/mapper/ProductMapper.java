package com.phone.library.mapper;

import com.phone.library.dto.ProductDto;
import com.phone.library.entity.ProductEntity;
import com.phone.library.entity.StoreEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity entity = new ProductEntity();
        entity.setProductName(dto.getProductName());
        entity.setDescription(dto.getDescription());
        entity.setCostPrice(dto.getCostPrice());
        entity.setSalePrice(dto.getSalePrice());
        entity.set_deleted(dto.is_deleted());
        entity.set_activated(dto.is_activated());
        if (dto.getCategory() != null) {
            entity.setCategory(dto.getCategory());
        }

        if (dto.getBrand() != null) {
            entity.setBrand(dto.getBrand());
        }

        return entity;
    }

    public ProductEntity toEntity(ProductEntity entityNew, ProductDto dto) {
        entityNew.setProductName(dto.getProductName());
        entityNew.setDescription(dto.getDescription());
        entityNew.setCostPrice(dto.getCostPrice());
        entityNew.setSalePrice(dto.getSalePrice());
        if (dto.getCategory() != null) {
            entityNew.setCategory(dto.getCategory());
        }
        if (dto.getBrand() != null) {
            entityNew.setBrand(dto.getBrand());
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
        dto.set_deleted(entity.is_deleted());
        dto.set_activated(entity.is_activated());

        if (entity.getCategory() != null) {
            dto.setCategory(entity.getCategory());
        }
        if (entity.getBrand() != null) {
            dto.setBrand(entity.getBrand());
        }
        int quantity = 0;
        if(entity.getStores() != null) {
            for (StoreEntity store: entity.getStores()) {
                quantity += store.getQuantity();
            }
        }
        dto.setQuantity(quantity);

        return dto;
    }
}
