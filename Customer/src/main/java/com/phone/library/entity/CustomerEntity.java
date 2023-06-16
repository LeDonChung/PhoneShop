package com.phone.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String phone;

    private String username; // phone or email

    private String password;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    private String email;

    private boolean gender;

    private String birthDate;

    private String providerId;

    private String address;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customers_favorites", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
    private List<ProductEntity> favorites;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "customers_roles", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<RoleEntity> roles;

}
