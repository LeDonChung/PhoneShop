package com.fashion.library.mapper;

import com.fashion.library.dto.RoleDto;
import com.fashion.library.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    RoleDto toDto(RoleEntity entity) {
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }

    RoleEntity toEntity(RoleDto dto) {
        RoleEntity entity = new RoleEntity();
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        return entity;
    }

}
