package com.phone.library.mapper;

import com.phone.library.dto.BrandDto;
import com.phone.library.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public BrandDto toDto(BrandEntity entity) {
        BrandDto dto = new BrandDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setLogo(entity.getLogo());
        dto.set_activated(entity.is_activated());
        dto.set_deleted(entity.is_deleted());
        return dto;
    }

    public BrandEntity toEntity(BrandDto dto) {
        BrandEntity entity = new BrandEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setLogo(dto.getLogo());
        return entity;
    }

    public BrandEntity toEntity(BrandEntity entity, BrandDto dto) {
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setLogo(dto.getLogo());
        return entity;
    }

}
