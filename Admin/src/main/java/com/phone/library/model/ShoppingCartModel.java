package com.phone.library.model;

import com.phone.library.entity.CustomerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartModel {

    private Long id;

    private int totalItem;

    private double totalPrice;

}
