package com.zosh.service.serviceInterface;

import com.zosh.dto.request.CreateFoodRequest;
import com.zosh.model.Category;
import com.zosh.model.Food;
import com.zosh.model.Restaurant;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId)throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,boolean isVegitarian,
                                         boolean isNonveg,boolean isSeasonal,
                                         String foodCategory);
    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailability(Long foodId) throws Exception;
}
