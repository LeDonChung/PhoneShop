package com.phone.library.repository;

import com.phone.library.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "select c from CustomerEntity c where c.username = ?1")
    CustomerEntity findByUsername(String username);
}
