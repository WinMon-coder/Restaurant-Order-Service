package com.Group3.OrderService.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.repository.FoodRepository;
import com.Group3.OrderService.service.CategoryService;
import com.Group3.OrderService.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService {
	@Autowired
	FoodRepository foodRepository;
	@Autowired 
	CategoryService categoryService;

	@Autowired
	ModelMapper modelMapper;

	@Override // save in database
	public Food createFood(FoodDto foodDto) {
		return foodRepository.save(mapToEntity(foodDto));
	}

	@Override // get from database
	public List<FoodDto> getAllFoods() {
		List<Food> foodList = foodRepository.findAll();
		List<FoodDto> foodDtoList = foodList.stream().map(food -> mapToDTO(food)).collect(Collectors.toList());

		return foodDtoList;
	}
	@Override
	public List<FoodDto> findAllFoodIsAvaliable() {
		List<Food> foodList = foodRepository.findAll();
		List<FoodDto> foodDtoList = foodList.stream()
				.filter(food->food.getIsAvaliable() == true)
				.map(food -> mapToDTO(food))
				.collect(Collectors.toList());
		
		return foodDtoList;
	}

	@Override
	public FoodDto getFood(int id) {
		Food food = foodRepository.findById(id).orElse(null);
		return mapToDTO(food);
	}

	@Override
	public Food updateFood(int id, FoodDto foodDto) {
		FoodDto orginalFoodDto = this.getFood(id);
		Category category = categoryService.getCategory(foodDto.getFoodCategory().getCategoryId());
		if (orginalFoodDto != null) {
			orginalFoodDto.setFoodName(foodDto.getFoodName());
			orginalFoodDto.setFoodPhoto(foodDto.getFoodPhoto());
			orginalFoodDto.setFoodCategory(category);
			orginalFoodDto.setFoodPrice(foodDto.getFoodPrice());
			orginalFoodDto.setIsAvaliable(foodDto.getIsAvaliable());
		}
		return foodRepository.save(mapToEntity(orginalFoodDto));
	}

	@Override
	public Boolean deleteFood(int id) {
		Food food = foodRepository.findById(id).orElse(null);
		if (food == null) {
			return false;
		}
		foodRepository.deleteById(id);
		return true;
	}

	@Override
	public List<FoodDto> getAllFoodByCategory(Category category) {
		List<FoodDto> foodList = foodRepository.findByFoodCategory(category).stream()
		.filter(food->food.getIsAvaliable() == true)
		.map(food -> mapToDTO(food))
		.collect(Collectors.toList());
		return foodList;
	}

	
	@Override
	public List<Food> getAllFoodLikeByFoodName(String foodName) {
		return foodRepository.findByNameLike(foodName);
	}

	// convert dto to entity
	private Food mapToEntity(FoodDto foodDto) {

		return modelMapper.map(foodDto, Food.class);

	}

	// convert entity to dto
	private FoodDto mapToDTO(Food food) {
		return modelMapper.map(food, FoodDto.class);

	}

	@Override
	public Food getByFoodName(String foodName) {
		return foodRepository.findByFoodName(foodName);
	}

	@Override
	public List<Food> getAllFoodByFoodName(String foodName) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
