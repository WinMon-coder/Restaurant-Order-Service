package com.Group3.OrderService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.repository.FoodRepository;
import com.Group3.OrderService.service.CategoryService;
import com.Group3.OrderService.service.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {

	@Autowired
	FoodService foodService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	FoodRepository foodRepository;

	/***** getFoodsByCategory ******************************/
	@GetMapping(value="/foodName/{foodName}")
	public ResponseEntity<Boolean>findFoodByName(@PathVariable ("foodName")String foodName){
		
		Food food = foodService.getByFoodName(foodName);
		if(food == null) {
			return ResponseEntity.ok().body(false);
		}
		return ResponseEntity.ok().body(true);
	}
	@GetMapping("/category/{id}")
	public ResponseEntity<?> getFoodsByCategory(@PathVariable int id) {
		Category category = categoryService.getCategory(id);
		if (category == null) {
			return ResponseEntity.badRequest().body("Category ID is invalid");
		}
		List<FoodDto> foodList = foodService.getAllFoodByCategory(category);
		return ResponseEntity.ok().body(foodList);
	}

	/***** GetAllFoodList ******************************/
	@GetMapping(value = "/list")
	public ResponseEntity<List<FoodDto>> getAllFood() {
		return ResponseEntity.ok().body(foodService.getAllFoods());

	}
	/***** GetAllFoodListWithAvaliable ******************************/
	@GetMapping(value = "/list_with_avaliable")
	public ResponseEntity<List<FoodDto>> getAllFoodWithAvaliable() {
		return ResponseEntity.ok().body(foodService.findAllFoodIsAvaliable());

	}

	/***** GetFood ******************************/
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<FoodDto> getFood(@PathVariable int id) {
		FoodDto foodDto = foodService.getFood(id);
		if (foodDto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(foodDto);

	}

	/***** GetFoodsByName ******************************/
	@GetMapping(value = "/name")
	public ResponseEntity<List<Food>> getFoosByName(@RequestParam String foodName) {
		return ResponseEntity.ok().body(foodService.getAllFoodByFoodName(foodName));

	}

	/***** GetFoodsByName ******************************/
	@GetMapping(value = "/foodName")
	public ResponseEntity<List<Food>> getAllFoodLikeByFoodName(@RequestParam String foodName) {
		return ResponseEntity.ok().body(foodService.getAllFoodLikeByFoodName(foodName));

	}
}
