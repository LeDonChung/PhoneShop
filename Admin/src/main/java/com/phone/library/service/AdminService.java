package com.phone.library.service;

import com.phone.library.dto.AdminDto;

public interface AdminService {
    AdminDto findByUserName(String username);

    AdminDto save(AdminDto adminDto);
}
