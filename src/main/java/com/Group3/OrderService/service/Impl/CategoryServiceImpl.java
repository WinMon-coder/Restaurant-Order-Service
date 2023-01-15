package com.Group3.OrderService.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.repository.CategoryRepository;
import com.Group3.OrderService.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategory(int id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category updateCategory(int id, Category category) {
		Category orginalCategory = this.getCategory(id);
		if (orginalCategory == null) {
			return null;
		}
		orginalCategory.setCategoryId(id);
		orginalCategory.setCategoryName(category.getCategoryName());
		orginalCategory.setCategoryPhoto(category.getCategoryPhoto());
		orginalCategory = categoryRepository.save(orginalCategory);

		return orginalCategory;

	}

	@Override
	public Boolean deleteCategory(int id) {
		Category category = this.getCategory(id);
		if (category == null) {
			return false;
		}
		categoryRepository.deleteById(id);
		return true;
	}

	@Override
	public Category getByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}
	

}
