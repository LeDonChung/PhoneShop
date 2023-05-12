package com.phone.library.repository;

import com.phone.library.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByUsername(String username);
}
