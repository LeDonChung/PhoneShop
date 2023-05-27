package com.phone.library.dto;

import com.phone.library.entity.CustomerEntity;
import com.phone.library.entity.OrderDetailEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    private Date orderDate;

    private Date deliveryDate;

    private String address;

    private String phoneNumber;

    private double totalPrice;

    private double shippingFee;

    private String orderStatus;

    private String notes;

    private CustomerEntity customer;

    private List<OrderDetailEntity> orderDetails;
}
