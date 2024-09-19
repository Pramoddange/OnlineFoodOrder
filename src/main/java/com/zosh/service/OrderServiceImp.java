package com.zosh.service;

import com.zosh.dto.request.OrderRequest;
import com.zosh.model.*;
import com.zosh.repository.AddressRepository;
import com.zosh.repository.OrderItemRepository;
import com.zosh.repository.OrderRepository;
import com.zosh.repository.UserRepository;
import com.zosh.service.serviceInterface.CartService;
import com.zosh.service.serviceInterface.OrderService;
import com.zosh.service.serviceInterface.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippAddress =order.getDeliveryAddress();

        Address saveAddress=addressRepository.save(shippAddress);

        if(!user.getAddresses().contains(saveAddress)){
            user.getAddresses().add(saveAddress);
            userRepository.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());

        Order createOrder=new Order();
        createOrder.setCustomer(user);
        createOrder.setOrderStatus("PENDING");
        createOrder.setCreatedAt(new Date());
        createOrder.setDeliveryAddress(saveAddress);
        createOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItemsList=new ArrayList<>();
        for(CartItem cartItem:cart.getItem()){
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem saveOrderItem=orderItemRepository.save(orderItem);
            orderItemsList.add(saveOrderItem);
        }
        Long totalPrice=cartService.calculateCartTotals(cart);
        createOrder.setItem(orderItemsList);
        createOrder.setTotalPrice(totalPrice);

        Order saveOrder=orderRepository.save(createOrder);
        restaurant
        return null;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {

    }

    @Override
    public List<Order> getUserOrder(Long userId) throws Exception {
        return null;
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        return null;
    }
}
