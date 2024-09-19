package com.zosh.controller;

import com.zosh.dto.request.CreateRestaurantRequest;
import com.zosh.dto.response.MessageResponse;
import com.zosh.model.Restaurant;
import com.zosh.model.User;
import com.zosh.service.serviceInterface.RestaurantService;
import com.zosh.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization") String jwt)throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.createRestaurant(req,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id)throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id)throws Exception {
        User user=userService.findUserByJwtToken(jwt);

       restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse=new MessageResponse();
        messageResponse.setMessage("restaurant delete successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.MOVED_PERMANENTLY);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
                                                            @RequestHeader("Authorization") String jwt,
                                                            @PathVariable Long id)throws Exception {
        User user=userService.findUserByJwtToken(jwt);

       Restaurant restaurant=restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
                                                             @RequestHeader("Authorization") String jwt)throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
