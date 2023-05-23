package com.phone.library.service;

import com.phone.library.dto.CustomerDto;

public interface CustomerService {
    CustomerDto save(CustomerDto dto);
    CustomerDto findByUsername(String username);

}
