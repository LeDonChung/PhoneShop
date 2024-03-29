package com.phone.library.service;

import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.ShoppingCartModel;

import java.util.List;
import java.util.Set;

public interface OrderService {
    // Customer
    List<OrderDto> findByCustomerId(Long id);
    OrderDto findById(Long id);
    OrderDto updateStatus(Long orderId, String status);
    OrderDto saveOrder(ShoppingCartModel cart, Set<CartItemModel> cartItems, CustomerDto customer, String payment, String notes);
    List<OrderDto> findAll();

}
