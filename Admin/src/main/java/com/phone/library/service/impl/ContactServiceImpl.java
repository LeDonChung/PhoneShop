package com.phone.library.service.impl;

import com.phone.library.dto.ContactDto;
import com.phone.library.entity.ContactEntity;
import com.phone.library.mapper.ContactMapper;
import com.phone.library.repository.ContactRepository;
import com.phone.library.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;


    @Override
    public ContactDto save(ContactDto contactDto) {
        ContactEntity entity = contactMapper.toEntity(contactDto);
        return contactMapper.toDto(contactRepository.save(entity));
    }
}
