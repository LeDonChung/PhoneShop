package com.phone.library.mapper;

import com.phone.library.dto.OrderDto;
import com.phone.library.entity.OrderEntity;
import org.springframework.stereotype.Component;


@Component
public class OrderMapper {
    public OrderDto toDto(OrderEntity entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setDeliveryDate(entity.getOrderDate());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setShippingFee(entity.getShippingFee());
        dto.setPaymentStatus(entity.isPaymentStatus());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setNotes(entity.getNotes());
        if(entity.getCustomer() != null) {
            dto.setCustomer(entity.getCustomer());
        }
        if(entity.getOrderDetails() != null) {
            dto.setOrderDetails(entity.getOrderDetails());

        }
        return dto;
    }

    public OrderEntity toEntity(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderDate(dto.getOrderDate());
        entity.setDeliveryDate(dto.getOrderDate());
        entity.setAddress(dto.getAddress());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setShippingFee(dto.getShippingFee());
        entity.setOrderStatus(dto.getOrderStatus());
        entity.setPaymentStatus(dto.isPaymentStatus());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setNotes(dto.getNotes());
        if(dto.getCustomer() != null) {
            entity.setCustomer(dto.getCustomer());
        }
        if(dto.getOrderDetails() != null) {
            entity.setOrderDetails(dto.getOrderDetails());
        }
        return entity;
    }
}
