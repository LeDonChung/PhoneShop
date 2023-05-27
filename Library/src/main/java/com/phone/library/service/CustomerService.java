package com.phone.library.service;

import com.phone.library.dto.CustomerDto;

public interface CustomerService {
    CustomerDto save(CustomerDto dto);
    CustomerDto findByUsername(String username);
    boolean addFavorite(Long productId, CustomerDto customerDto);
    boolean removeFavorite(Long productId, CustomerDto customerDto);
    CustomerDto changePassword(CustomerDto customerDto, String newPassword);


}
