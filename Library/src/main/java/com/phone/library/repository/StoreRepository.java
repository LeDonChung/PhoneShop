package com.phone.library.repository;

import com.phone.library.entity.AdminEntity;
import com.phone.library.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
}
