package com.phone.library.mapper;

import com.phone.library.dto.ContactDto;
import com.phone.library.entity.ContactEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactEntity toEntity(ContactDto dto) {
        ContactEntity entity = new ContactEntity();
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setMessage(dto.getMessage());
        entity.setSubject(dto.getSubject());
        return entity;
    }
    public ContactDto toDto(ContactEntity entity) {
        ContactDto dto = new ContactDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setSubject(entity.getSubject());
        return dto;
    }

}
