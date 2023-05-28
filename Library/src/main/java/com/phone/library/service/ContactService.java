package com.phone.library.service;

import com.phone.library.dto.ContactDto;
import com.phone.library.entity.ContactEntity;

public interface ContactService {
    ContactDto save(ContactDto contactDto);
}
