package com.phone.library.repository;

import com.phone.library.entity.AddressDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDeliveryRepository extends JpaRepository<AddressDeliveryEntity, Long> {
}
