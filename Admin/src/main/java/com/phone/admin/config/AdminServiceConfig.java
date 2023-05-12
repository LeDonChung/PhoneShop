package com.phone.admin.config;

import com.phone.library.entity.AdminEntity;
import com.phone.library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class AdminServiceConfig implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByUsername(username);
        System.out.println(admin);
        if(admin == null) {
            throw new UsernameNotFoundException("Could not find username");
        }
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                admin.getRoles()
                        .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
