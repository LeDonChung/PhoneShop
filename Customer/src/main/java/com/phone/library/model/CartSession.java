package com.phone.library.model;

import com.phone.library.constants.SystemConstants;
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
    public boolean addItemToCart(HttpSession session,
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
        StoreEntity store = storeRepository.getStoreByProductIdAndColorCodeAndStorageCode(productDto.getId(), productDto.getColorCode(), productDto.getStorageCode());
        if(store == null) {
            return false;
        }
        double price = store.getSalePrice() == 0 ? store.getCostPrice() : store.getSalePrice();
        if(cartItem == null) {
            cartItem = new CartItemModel();
            cartItem.setQuantity(quantity);
            cartItem.setColor(colorRepository.findByCode(productDto.getColorCode()));
            cartItem.setStorage(storageRepository.findByCode(productDto.getStorageCode()));
            cartItem.setMemory(memoryRepository.findByCode(productDto.getMemory().getCode()));
            cartItem.setTotalPrice(quantity * price);
            cartItem.setProductDto(productDto);
            cartItem.setUnitPrice(price);
            cartItems.add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(cartItem.getQuantity() * price);

            cartItems.removeIf(cartItemModel -> cartItemModel.getProductDto().getId() == productDto.getId()
                    && cartItemModel.getColor().getCode().equals(productDto.getColorCode())
                    && cartItemModel.getStorage().getCode().equals(productDto.getStorageCode()));
        }
        cartItems.add(cartItem);

        cart.setTotalItem(getTotalItem(cartItems));
        cart.setTotalPrice(getToTalPrice(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        return true;
    }

    public boolean updateItemToCart(HttpSession session,
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
        StoreEntity store = storeRepository.getStoreByProductIdAndColorCodeAndStorageCode(productDto.getId(), productDto.getColorCode(), productDto.getStorageCode());

        double price = store.getSalePrice() == 0 ? store.getCostPrice() : store.getSalePrice();

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getQuantity() * price);

        cartItems.removeIf(cartItemModel -> cartItemModel.getProductDto().getId() == productDto.getId()
                && cartItemModel.getColor().getCode().equals(productDto.getColorCode())
                && cartItemModel.getStorage().getCode().equals(productDto.getStorageCode()));

        cartItems.add(cartItem);

        cart.setTotalItem(getTotalItem(cartItems));
        cart.setTotalPrice(getToTalPrice(cartItems));

        session.setAttribute(SystemConstants.SHOPPING_CART, cart);
        session.setAttribute(SystemConstants.CART_ITEMS, cartItems);
        return true;
    }

    public void removeAll(HttpSession session) {
        session.removeAttribute(SystemConstants.SHOPPING_CART);
        session.removeAttribute(SystemConstants.CART_ITEMS);
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
                && cartItemModel.getColor().getCode().equals(productDto.getColorCode())
                && cartItemModel.getStorage().getCode().equals(productDto.getStorageCode()));

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
                    && cartItem.getColor().getCode().equals(product.getColorCode())
                    && cartItem.getStorage().getCode().equals(product.getStorageCode())) {
                model = cartItem;
            }
        }
        return model;
    }
}
