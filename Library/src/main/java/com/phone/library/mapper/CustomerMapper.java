package com.phone.library.mapper;

import com.phone.library.dto.AdminDto;
import com.phone.library.dto.CustomerDto;
import com.phone.library.entity.AdminEntity;
import com.phone.library.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public CustomerDto toDto(CustomerEntity entity) {
        CustomerDto dto = new CustomerDto();
        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setGender(entity.isGender());
        dto.setAddress(entity.getAddress());
        return dto;
    }

    public CustomerEntity toEntity(CustomerDto dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setImage(dto.getImage());
        entity.setPassword(dto.getPassword());
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setGender(dto.isGender());
        entity.setAddress(dto.getAddress());
        return entity;
    }
}
