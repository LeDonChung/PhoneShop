package com.phone.library.service;

import com.phone.library.dto.CustomerDto;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    CustomerDto save(CustomerDto dto);
    CustomerDto saveImage(CustomerDto dto, MultipartFile imageUser);
    CustomerDto findByUsername(String username);
    boolean addFavorite(Long productId, CustomerDto customerDto);
    boolean removeFavorite(Long productId, CustomerDto customerDto);
    CustomerDto changePassword(CustomerDto customerDto, String newPassword);
    CustomerDto findByUsernameAndProviderId(String username, String providerId);

}
