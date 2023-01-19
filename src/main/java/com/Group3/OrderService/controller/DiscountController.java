package com.Group3.OrderService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Group3.OrderService.entity.Discount;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.repository.DiscountRepository;
import com.Group3.OrderService.service.DiscountService;

@RestController
@RequestMapping("/discount")
public class DiscountController {
	@Autowired
	DiscountService discountService;
	@Autowired
	DiscountRepository discountRepository;
	
	/***** getFoodsByCategory ******************************/
	@GetMapping(value="/discountName/{discountName}")
	public ResponseEntity<Boolean>findDiscountByName(@PathVariable ("discountName")String discountName){
		
		Discount discount = discountService.getByDiscountName(discountName);
		if(discount == null) {
			return ResponseEntity.ok().body(false);
		}
		return ResponseEntity.ok().body(true);
	}

	/***** GetAllCategoryList ******************************/
	@GetMapping(value = "/list")
	public ResponseEntity<List<Discount>> getAllDiscounts() {
		List<Discount> discounts = discountService.getAllDiscounts();
		return ResponseEntity.ok().body(discounts);

	}
	/***** GetCategory ******************************/
	@GetMapping(value = "/{id}")
	public ResponseEntity<Discount> getDiscount(@PathVariable int id) {
		Discount discount = discountService.getDiscount(id);
		if (discount == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(discount);

	}
	
	@GetMapping(value="/active")
	public ResponseEntity<Discount> findActiveDiscount(){
				
		return ResponseEntity.ok().body(discountService.findDiscountOn());
	}
	
}
