package com.zosh.service.serviceInterface;

import com.zosh.dto.request.AddCartItemRequest;
import com.zosh.model.Cart;
import com.zosh.model.CartItem;

public interface CartService {
    public CartItem addItemToCart(AddCartItemRequest req, String jwt)throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;

    public Long calculateCartTotals(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userId)throws Exception;

    public Cart clearCart(Long userId)throws Exception;
}
