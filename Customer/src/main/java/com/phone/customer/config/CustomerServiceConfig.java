package com.phone.customer.config;

import com.phone.library.entity.AdminEntity;
import com.phone.library.entity.CustomerEntity;
import com.phone.library.repository.AdminRepository;
import com.phone.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

public class CustomerServiceConfig implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity customer = customerRepository.findByUsername(username);
        System.out.println(customer);
        if(customer == null) {
            throw new UsernameNotFoundException("Could not find username");
        }
        return new User(
                customer.getUsername(),
                customer.getPassword(),
                customer.getRoles()
                        .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
