package com.zosh.service.serviceInterface;

import com.zosh.model.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(String name,long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long id)throws Exception;

    public Category findCategoryById(Long id) throws Exception;
}
