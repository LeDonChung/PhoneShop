package com.phone.library.mapper;

import com.phone.library.dto.ColorDto;
import com.phone.library.entity.ColorEntity;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper {
    public ColorDto toDto(ColorEntity entity) {
        ColorDto dto = new ColorDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        return dto;
    }

    public ColorEntity toEntity(ColorDto dto) {
        ColorEntity entity = new ColorEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }
}
