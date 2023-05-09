package com.fashion.library.service;

import com.fashion.library.dto.AdminDto;

public interface AdminService {
    AdminDto findByUserName(String username);

    AdminDto save(AdminDto adminDto);
}
