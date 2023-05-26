package com.phone.library.model;

import com.phone.library.constants.SystemConstants;
import com.phone.library.dto.CustomerDto;
import com.phone.library.dto.ProductDto;
import com.phone.library.entity.StoreEntity;
import com.phone.library.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CartSession {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private MemoryRepository memoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;
    public void addItemToCart(HttpSession session,
                                           ProductDto productDto,
                                           int quantity) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        CartItemModel cartItem = getCartItem(cartItems, productDto);
        StoreEntity store = storeRepository.getStorageByProductIdAndColorCodeAndStorageCode(productDto.getId(), productDto.getColorCode(), productDto.getStorageCode());
        double price = store.getSalePrice() == 0 ? store.getCostPrice() : store.getSalePrice();
        if(cartItem == null) {
            cartItem = new CartItemModel();
            cartItem.setQuantity(quantity);
            cartItem.setColorCode(productDto.getColorCode());
            cartItem.setStorageCode(productDto.getStorageCode());
            cartItem.setMemoryCode(productDto.getMemory().getCode());
            cartItem.setTotalPrice(quantity * price);
            cartItem.setProductDto(productDto);
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getQuantity() * price);

            cartItems.removeIf(cartItemModel -> cartItemModel.getProductDto().getId() == productDto.getId()
                    && cartItemModel.getColorCode().equals(productDto.getColorCode())
                    && cartItemModel.getStorageCode().equals(productDto.getStorageCode()));
        }
        cartItems.add(cartItem);

        cart.setTotalItem(getTotalItem(cartItems));
        cart.setTotalPrice(getToTalPrice(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
    }

    public void updateItemToCart(HttpSession session,
                              ProductDto productDto,
                              int quantity) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        CartItemModel cartItem = getCartItem(cartItems, productDto);
        StoreEntity store = storeRepository.getStorageByProductIdAndColorCodeAndStorageCode(productDto.getId(), productDto.getColorCode(), productDto.getStorageCode());
        double price = store.getSalePrice() == 0 ? store.getCostPrice() : store.getSalePrice();

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setTotalPrice(cartItem.getQuantity() * price);

        cartItems.removeIf(cartItemModel -> cartItemModel.getProductDto().getId() == productDto.getId()
                && cartItemModel.getColorCode().equals(productDto.getColorCode())
                && cartItemModel.getStorageCode().equals(productDto.getStorageCode()));

        cartItems.add(cartItem);

        cart.setTotalItem(getTotalItem(cartItems));
        cart.setTotalPrice(getToTalPrice(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
    }

    public void deleteToCart(HttpSession session,
                                 ProductDto productDto) {
        ShoppingCartModel cart = (ShoppingCartModel) session.getAttribute(SystemConstants.SHOPPING_CART);
        Set<CartItemModel> cartItems = (Set<CartItemModel>) session.getAttribute(SystemConstants.CART_ITEMS);
        if(cart == null) {
            cart = new ShoppingCartModel();
        }
        if(cartItems == null) {
            cartItems = new HashSet<>();
        }

        cartItems.removeIf(cartItemModel -> cartItemModel.getProductDto().getId() == productDto.getId()
                && cartItemModel.getColorCode().equals(productDto.getColorCode())
                && cartItemModel.getStorageCode().equals(productDto.getStorageCode()));

        cart.setTotalItem(getTotalItem(cartItems));
        cart.setTotalPrice(getToTalPrice(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
    }

    public int getTotalItem(Set<CartItemModel> cartItems) {
        int totalItem = 0;
        for (CartItemModel cartItem: cartItems) {
            totalItem += cartItem.getQuantity();
        }
        return totalItem;
    }
    public double getToTalPrice(Set<CartItemModel> cartItems) {
        double totalPrice = 0;
        for (CartItemModel cartItem: cartItems) {
            totalPrice += cartItem.getTotalPrice();
        }
        return totalPrice;
    }
    public CartItemModel getCartItem(Set<CartItemModel> cartItems,
                                     ProductDto product) {
        if(cartItems == null) {
            return null;
        }

        CartItemModel model = null;
        for (CartItemModel cartItem: cartItems) {
            if(cartItem.getProductDto().getId() == product.getId()
            && cartItem.getColorCode().equals(product.getColorCode())
            && cartItem.getStorageCode().equals(product.getStorageCode())) {
                model = cartItem;
            }
        }
        return model;
    }
}
