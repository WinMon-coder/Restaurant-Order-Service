package com.Group3.OrderService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.Category;
@EnableJpaRepositories
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	public Category findByCategoryName(String categoryName);

}
