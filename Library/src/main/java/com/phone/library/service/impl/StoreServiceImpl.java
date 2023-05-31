package com.phone.library.service.impl;

import com.phone.library.dto.ColorDto;
import com.phone.library.dto.MemoryDto;
import com.phone.library.dto.StorageDto;
import com.phone.library.dto.StoreDto;
import com.phone.library.entity.*;
import com.phone.library.mapper.ColorMapper;
import com.phone.library.mapper.MemoryMapper;
import com.phone.library.mapper.StorageMapper;
import com.phone.library.mapper.StoreMapper;
import com.phone.library.repository.StoreRepository;
import com.phone.library.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private StorageMapper storageMapper;
    @Autowired
    private ColorMapper colorMapper;

    @Override
    public boolean delete(Long id) {
        StoreEntity store = storeRepository.findById(id).get();
        if(store == null) {
            return false;
        }

        storeRepository.delete(store);
        return true;
    }

    @Override
    public List<StoreDto> findAll() {
        List<StoreDto> dtos = new ArrayList<>();
        List<StoreEntity> entities = storeRepository.findAll();
        for (StoreEntity entity : entities) {
            dtos.add(storeMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public StoreDto save(StoreDto dto) {
        StoreEntity entity = storeRepository.getByProductIdAndColorCodeAndStorageCode(dto.getProduct().getId(), dto.getColor().getCode(), dto.getStorage().getCode());
        if(entity != null) {
            entity.setQuantity(entity.getQuantity() + dto.getQuantity());
        } else {
            entity = storeMapper.toEntity(dto);
        }
        return storeMapper.toDto(storeRepository.save(entity));
    }

    @Override
    public int getQuantityByProductIdAndStorageCodeAndColorCode(Long productId, String storageCode, String colorCode) {
        int quantity = 0;
        StoreEntity store = storeRepository.getByProductIdAndColorCodeAndStorageCode(productId, colorCode, storageCode);
        if (store != null) {
            quantity = store.getQuantity();
        }
        return quantity;
    }

    @Override
    public StoreDto findByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<StoreDto> findByProductId(Long productId) {
        List<StoreDto> dtos = new ArrayList<>();
        List<StoreEntity> entities = storeRepository.findByProductId(productId);
        if (entities != null) {
            for (StoreEntity entity : entities) {
                dtos.add(storeMapper.toDto(entity));
            }
        }
        return dtos;
    }

    @Override
    public List<StorageDto> findStoragesByProductId(Long id) {
        List<StorageEntity> entities = storeRepository.findStoragesByProductId(id);
        List<StorageDto> dtos = new ArrayList<>();
        if (entities != null) {
            for (StorageEntity entity : entities) {
                dtos.add(storageMapper.toDto(entity));
            }
        }
        return dtos;
    }

    @Override
    public List<ColorDto> findColorsByStorageCodeAndProductId(String storageCode, Long productId) {
        List<ColorEntity> entities = storeRepository.findColorsByStorageCodeAndProductId(storageCode, productId);
        List<ColorDto> dtos = new ArrayList<>();
        if (entities != null) {
            for (ColorEntity entity : entities) {
                dtos.add(colorMapper.toDto(entity));
            }
        }
        return dtos;
    }

    @Override
    public Double getPriceByProductIdAndColorCodeAndStorageCode(Long productId, String colorCode, String storageCode) {
        StoreEntity entity = storeRepository.getStoreByProductIdAndColorCodeAndStorageCode(productId, colorCode, storageCode);
        return entity.getSalePrice() == 0 ? entity.getCostPrice() : entity.getSalePrice();
    }

}
