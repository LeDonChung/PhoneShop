package com.phone.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Date birthDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_delivery_id", referencedColumnName = "address_delivery_id")
    private AddressDeliveryEntity addressDeliveryMain;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customers_roles", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<RoleEntity> roles;

}
