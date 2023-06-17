package com.phone.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phone.library.entity.CustomerEntity;
import com.phone.library.entity.OrderDetailEntity;
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

    private String paymentMethod;

    private boolean paymentStatus;

    private String notes;

    @JsonIgnore
    private CustomerEntity customer;

    @JsonIgnore
    private List<OrderDetailEntity> orderDetails;
}
