package com.phone.library.mapper;

import com.phone.library.dto.CommentDto;
import com.phone.library.entity.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setStars(entity.getStars());
        if(entity.getProduct() != null) {
            dto.setProduct(entity.getProduct());
        }
        dto.setFullName(entity.getFullName());
        dto.setCommentDate(entity.getCommentDate());
        return dto;
    }


    public CommentEntity toEntity(CommentDto dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setStars(dto.getStars());
        if(dto.getProduct() != null) {
            entity.setProduct(dto.getProduct());
        }
        entity.setFullName(dto.getFullName());
        return entity;
    }
}
