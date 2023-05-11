package com.fashion.library.service;

import com.fashion.library.dto.CategoryDto;
import com.fashion.library.entity.ColorEntity;

import java.util.List;

public interface ColorService {
    List<ColorEntity> findAll();
}
