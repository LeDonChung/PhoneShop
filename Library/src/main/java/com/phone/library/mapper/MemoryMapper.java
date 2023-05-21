package com.phone.library.mapper;

import com.phone.library.dto.BrandDto;
import com.phone.library.dto.MemoryDto;
import com.phone.library.entity.BrandEntity;
import com.phone.library.entity.MemoryEntity;
import org.springframework.stereotype.Component;

@Component
public class MemoryMapper {
    public MemoryDto toDto(MemoryEntity entity) {
        MemoryDto dto = new MemoryDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        return dto;
    }

    public MemoryEntity toEntity(MemoryDto dto) {
        MemoryEntity entity = new MemoryEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }
}
