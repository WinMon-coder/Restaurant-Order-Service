package com.Group3.OrderService.service;

import java.util.List;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Food;

public interface FoodService {
	Food createFood(FoodDto foodDto);

	List<FoodDto> getAllFoods();

	FoodDto getFood(int id);

	Food updateFood(int id, FoodDto foodDto);

	Boolean deleteFood(int id);

	public Food getByFoodName(String foodName);

	public List<FoodDto> getAllFoodByCategory(Category category);

	public List<Food> getAllFoodByFoodName(String foodName);

	public List<Food> getAllFoodLikeByFoodName(String foodName);

	public List<FoodDto> findAllFoodIsAvaliable();
}
