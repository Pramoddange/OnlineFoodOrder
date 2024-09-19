package com.zosh.service;

import com.zosh.dto.request.AddCartItemRequest;
import com.zosh.model.Cart;
import com.zosh.model.CartItem;
import com.zosh.model.Food;
import com.zosh.model.User;
import com.zosh.repository.CartItemRepository;
import com.zosh.repository.CartRepository;
import com.zosh.repository.FoodRepository;
import com.zosh.service.serviceInterface.CartService;
import com.zosh.service.serviceInterface.FoodService;
import com.zosh.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private FoodService foodService;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Food food=foodService.findFoodById(req.getFoodId());

        Cart cart=cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem:cart.getItem()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem=new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem saveCartItem=cartItemRepository.save(newCartItem);
        cart.getItem().add(saveCartItem);

        return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem itemCart=cartItem.get();
        itemCart.setQuantity(quantity);
        //5*100=500
        itemCart.setTotalPrice(itemCart.getFood().getPrice()*quantity);
        return cartItemRepository.save(itemCart);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item=cartItem.get();
        cart.getItem().remove(item);

        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total=0L;

        for(CartItem cartItem:cart.getItem()){
            total=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart=cartRepository.findById(id);

        if(optionalCart.isEmpty()){
            throw new Exception("Cart not found with id "+id);
        }

        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
       // User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

        Cart cart=findCartByUserId(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
