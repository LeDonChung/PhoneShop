package com.phone.library.service.impl;

import com.phone.library.dto.AdminDto;
import com.phone.library.entity.AdminEntity;
import com.phone.library.mapper.AdminMapper;
import com.phone.library.repository.AdminRepository;
import com.phone.library.repository.RoleRepository;
import com.phone.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
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
