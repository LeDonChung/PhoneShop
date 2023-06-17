package com.phone.library.service.impl;

import com.phone.library.constants.OrderStatus;
import com.phone.library.constants.PaymentMethod;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.OrderDto;
import com.phone.library.entity.OrderDetailEntity;
import com.phone.library.entity.OrderEntity;
import com.phone.library.mapper.OrderMapper;
import com.phone.library.model.CartItemModel;
import com.phone.library.model.ShoppingCartModel;
import com.phone.library.repository.CustomerRepository;
import com.phone.library.repository.OrderDetailRepository;
import com.phone.library.repository.OrderRepository;
import com.phone.library.repository.ProductRepository;
import com.phone.library.service.MailService;
import com.phone.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MailService mailService;
    @Override
    public List<OrderDto> findByCustomerId(Long id) {
        List<OrderDto> dtos = new ArrayList<>();
        List<OrderEntity> entities = orderRepository.findByCustomerId(id);
        for (OrderEntity entity : entities) {
            dtos.add(orderMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public OrderDto findById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id).get());
    }

    @Override
    public OrderDto updateStatus(Long orderId, String status) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        orderEntity.setOrderStatus(status);
        return orderMapper.toDto(orderRepository.save(orderEntity));
    }

    @Override
    public OrderDto saveOrder(ShoppingCartModel cart, Set<CartItemModel> cartItems, CustomerDto customer, String paymentMethod, String notes) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderDate(new Date());
        entity.setAddress(customer.getAddress());
        entity.setPhoneNumber(customer.getPhone());
        entity.setTotalPrice(cart.getTotalPrice());
        entity.setShippingFee(0);
        entity.setOrderStatus(OrderStatus.pending.name());
        entity.setPaymentMethod(paymentMethod);
        if(paymentMethod.equals(PaymentMethod.cash.name())) {
            entity.setPaymentStatus(false);
        } else {
            entity.setPaymentStatus(true);
        }
        entity.setNotes(notes);
        if (customer != null) {
            entity.setCustomer(customerRepository.findByUsername(customer.getUsername()));
        }
        List<OrderDetailEntity> orders = new ArrayList<>();
        if (cartItems != null) {
            for (CartItemModel cartItem : cartItems) {
                OrderDetailEntity orderDetail = new OrderDetailEntity();
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTotalPrice(cartItem.getTotalPrice());
                orderDetail.setUnitPrice(cartItem.getUnitPrice());
                orderDetail.setOrder(entity);
                orderDetail.setProduct(productRepository.findById(cartItem.getProductDto().getId()).get());
                orderDetail.setMemory(cartItem.getMemory());
                orderDetail.setStorage(cartItem.getStorage());
                orderDetail.setColor(cartItem.getColor());
                orderDetailRepository.save(orderDetail);
                orders.add(orderDetail);
            }
        }
        entity.setOrderDetails(orders);
        entity = orderRepository.save(entity);
        OrderDto dto = orderMapper.toDto(entity);

        mailService.sendEmailOrderSuccess(dto);

        return dto;
    }

    @Override
    public List<OrderDto> findAll() {
        List<OrderDto> results = new ArrayList<>();
        List<OrderEntity> orders = orderRepository.findAll();
        for (OrderEntity entity : orders) {
            results.add(orderMapper.toDto(entity));
        }
        return results;
    }
}
