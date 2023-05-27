package com.phone.library.service.impl;

import com.phone.library.dto.CustomerDto;
import com.phone.library.entity.CustomerEntity;
import com.phone.library.entity.ProductEntity;
import com.phone.library.mapper.CustomerMapper;
import com.phone.library.repository.CustomerRepository;
import com.phone.library.repository.ProductRepository;
import com.phone.library.repository.RoleRepository;
import com.phone.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProductRepository productRepository;

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

    @Override
    public boolean addFavorite(Long productId, CustomerDto customerDto) {
        List<ProductEntity> products = customerDto.getFavorites();
        ProductEntity productEntity = productRepository.findById(productId).get();
        if(products.contains(productEntity)) {
            return false;
        }
        products.add(productEntity);
        CustomerEntity customerEntity = customerRepository.findByUsername(customerDto.getUsername());
        customerEntity.setFavorites(products);
        customerRepository.save(customerEntity);
        return true;
    }
    @Override
    public boolean removeFavorite(Long productId, CustomerDto customerDto) {
        List<ProductEntity> products = customerDto.getFavorites();
        ProductEntity productEntity = productRepository.findById(productId).get();
        if(!products.contains(productEntity)) {
            return false;
        }
        products.remove(productEntity);
        CustomerEntity customerEntity = customerRepository.findByUsername(customerDto.getUsername());
        customerEntity.setFavorites(products);
        customerRepository.save(customerEntity);
        return true;
    }

}
