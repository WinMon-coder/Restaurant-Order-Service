package com.Group3.OrderService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Food;

@EnableJpaRepositories
@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
	
	public List<Food> findByFoodCategory(Category category);

	public Food findByFoodName(String foodName);
	
	public List<Food> findFoodByFoodId(int foodId);

	@Query("SELECT f FROM Food f " + "WHERE lower(f.foodName) LIKE lower(concat('%', :foodName, '%')) ")
	List<Food> findByNameLike(@Param("foodName") String foodName);
}
