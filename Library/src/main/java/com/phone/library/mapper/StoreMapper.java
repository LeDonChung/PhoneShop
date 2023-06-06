package com.phone.library.mapper;

import com.phone.library.dto.StoreDto;
import com.phone.library.entity.*;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {
    public StoreDto toDto(StoreEntity entity) {
        StoreDto dto = new StoreDto();
        dto.setId(entity.getId());
        dto.setSalePrice(entity.getSalePrice());
        dto.setCostPrice(entity.getCostPrice());
        dto.setQuantity(entity.getQuantity());
        if (entity.getProduct() != null) {
            dto.setProduct(entity.getProduct());
        }
        if (entity.getColor() != null) {
            dto.setColor(entity.getColor());
        }
        if (entity.getStorage() != null) {
            dto.setStorage(entity.getStorage());
        }
        return dto;
    }

    public StoreEntity toEntity(StoreDto dto) {
        StoreEntity entity = new StoreEntity();
        entity.setSalePrice(dto.getSalePrice());
        entity.setCostPrice(dto.getCostPrice());
        entity.setQuantity(dto.getQuantity());

        if (dto.getProduct() != null) {
            entity.setProduct(dto.getProduct());
        }
        if (dto.getColor() != null) {
            entity.setColor(dto.getColor());
        }
        if (dto.getStorage() != null) {
            entity.setStorage(dto.getStorage());
        }
        return entity;
    }

    public StoreEntity toEntity(StoreEntity entity, StoreDto dto) {
        entity.setSalePrice(dto.getSalePrice());
        entity.setCostPrice(dto.getCostPrice());
        entity.setQuantity(dto.getQuantity());

        if (dto.getProduct() != null) {
            entity.setProduct(dto.getProduct());
        }
        if (dto.getColor() != null) {
            entity.setColor(dto.getColor());
        }
        if (dto.getStorage() != null) {
            entity.setStorage(dto.getStorage());
        }
        return entity;
    }
}
