package com.phone.library.mapper;

import com.phone.library.dto.CustomerDto;
import com.phone.library.model.CustomerModel;
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
        if (entity.getFavorites() != null) {
            dto.setFavorites(entity.getFavorites());
        }
        return dto;
    }
    public CustomerDto toDto(CustomerModel model) {
        CustomerDto dto = new CustomerDto();
        dto.setId(model.getId());
        dto.setImage(model.getImage());
        dto.setPassword(model.getPassword());
        dto.setUsername(model.getUsername());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setBirthDate(model.getBirthDate());
        dto.setPhone(model.getPhone());
        dto.setEmail(model.getEmail());
        dto.setGender(model.isGender());
        dto.setAddress(model.getAddress());
        return dto;
    }
    public CustomerModel toModel(CustomerDto dto) {
        CustomerModel model = new CustomerModel();
        model.setId(dto.getId());
        model.setImage(dto.getImage());
        model.setPassword(dto.getPassword());
        model.setUsername(dto.getUsername());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setBirthDate(dto.getBirthDate());
        model.setPhone(dto.getPhone());
        model.setEmail(dto.getEmail());
        model.setGender(dto.isGender());
        model.setAddress(dto.getAddress());
        return model;
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
        if (dto.getFavorites() != null) {
            entity.setFavorites(dto.getFavorites());
        }
        return entity;
    }
}
