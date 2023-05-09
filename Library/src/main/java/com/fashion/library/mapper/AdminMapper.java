package com.fashion.library.mapper;

import com.fashion.library.dto.AdminDto;
import com.fashion.library.entity.AdminEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    public AdminDto toDto(AdminEntity entity) {
        AdminDto dto = new AdminDto();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return dto;
    }

    public AdminEntity toEntity(AdminDto dto) {
        AdminEntity entity = new AdminEntity();
        entity.setImage(dto.getImage());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return entity;
    }
}
