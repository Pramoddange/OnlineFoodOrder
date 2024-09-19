package com.zosh.controller;

import com.zosh.dto.request.CreateFoodRequest;
import com.zosh.dto.response.MessageResponse;
import com.zosh.model.Food;
import com.zosh.model.Restaurant;
import com.zosh.model.User;
import com.zosh.service.serviceInterface.FoodService;
import com.zosh.service.serviceInterface.RestaurantService;
import com.zosh.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food=foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserByJwtToken(jwt);

       foodService.deleteFood(id);
       MessageResponse res=new MessageResponse();
       res.setMessage("food deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PutMapping()
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt)throws Exception{
        User user=userService.findUserByJwtToken(jwt);


        Food food=foodService.updateAvailability(id);

        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

}
