package com.phone.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartModel {

    private Long id;

    private int totalItem;

    private double totalPrice;

}
