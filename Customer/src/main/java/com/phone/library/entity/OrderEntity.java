package com.phone.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Date orderDate;

    private Date deliveryDate;

    private String address;

    private String phoneNumber;

    private double totalPrice;

    private double shippingFee;

    private String orderStatus;

    private String notes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomerEntity customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<OrderDetailEntity> orderDetails;
}
