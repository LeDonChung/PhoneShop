package com.phone.library.mapper;

import com.phone.library.dto.ColorDto;
import com.phone.library.dto.StorageDto;
import com.phone.library.entity.ColorEntity;
import com.phone.library.entity.StorageEntity;
import org.springframework.stereotype.Component;

@Component
public class StorageMapper {
    public StorageDto toDto(StorageEntity entity) {
        StorageDto dto = new StorageDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        return dto;
    }

    public StorageEntity toEntity(StorageDto dto) {
        StorageEntity entity = new StorageEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }
}
