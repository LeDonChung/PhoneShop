package com.phone.library.service.impl;

import com.phone.library.dto.CustomerDto;
import com.phone.library.entity.CustomerEntity;
import com.phone.library.mapper.CustomerMapper;
import com.phone.library.repository.CustomerRepository;
import com.phone.library.repository.RoleRepository;
import com.phone.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public CustomerDto save(CustomerDto dto) {
        CustomerEntity entity = customerMapper.toEntity(dto);
        entity.setRoles(Collections.singletonList(roleRepository.findByName("CUSTOMER")));
        return customerMapper.toDto(customerRepository.save(entity));
    }

    @Override
    public CustomerDto findByUsername(String username) {
        CustomerEntity entity = customerRepository.findByUsername(username);
        CustomerDto dto = null;
        if(entity != null) {
            dto = customerMapper.toDto(entity);
        }
        return dto;
    }
}
