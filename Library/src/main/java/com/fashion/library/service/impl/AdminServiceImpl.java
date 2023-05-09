package com.fashion.library.service.impl;

import com.fashion.library.dto.AdminDto;
import com.fashion.library.entity.AdminEntity;
import com.fashion.library.mapper.AdminMapper;
import com.fashion.library.repository.AdminRepository;
import com.fashion.library.repository.RoleRepository;
import com.fashion.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AdminDto findByUserName(String username) {
        AdminEntity entity = adminRepository.findByUsername(username);
        AdminDto adminDto = null;
        if(entity != null) {
            adminDto = adminMapper.toDto(entity);
        }
        return adminDto;
    }

    @Override
    public AdminDto save(AdminDto adminDto) {
        AdminEntity entity = adminMapper.toEntity(adminDto);
        entity.setRoles(Collections.singletonList(roleRepository.findByName("ADMIN")));
        AdminEntity newEntity = adminRepository.save(entity);
        return adminMapper.toDto(newEntity);
    }
}
