package com.Group3.OrderService.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Discount;
import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.enumType.OrderStatus;
import com.Group3.OrderService.repository.DiscountRepository;
import com.Group3.OrderService.service.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
	@Autowired
	DiscountRepository discountRepository;

	@Override
	public Discount createDiscount(Discount discount) {
		discount.setIsActive(false);
		return discountRepository.save(discount);
	}

	@Override
	public List<Discount> getAllDiscounts() {
		// TODO Auto-generated method stub
		return discountRepository.findAll();
	}

	@Override
	public Discount getDiscount(int id) {
		// TODO Auto-generated method stub
		return discountRepository.findById(id).orElse(null);
	}

	@Override
	public Discount updateDiscount(int id, Discount discount) {
		Discount orginalDiscount = this.getDiscount(id);
		if (orginalDiscount == null) {
			return null;
		}
		orginalDiscount.setDiscountId(id);
		orginalDiscount.setDiscountPercentage(discount.getDiscountPercentage());
		orginalDiscount.setDiscountName(discount.getDiscountName());
		orginalDiscount.setMininumOrderAmount(discount.getMininumOrderAmount());
		orginalDiscount = discountRepository.save(orginalDiscount);
		return orginalDiscount;

	}

	@Override
	public Boolean deleteDiscount(int id) {
		Discount discount = this.getDiscount(id);
		if (discount == null) {
			return false;
		}
		discountRepository.deleteById(id);
		return true;
	}

	@Override
	public Discount getByDiscountName(String discountName) {
		// TODO Auto-generated method stubreturn
		// categoryRepository.findByCategoryName(categoryName);
		return discountRepository.findByDiscountName(discountName);
	}

	@Override
	public Discount updateActiveDiscount(int id, Discount discount) {
		Discount orginalDiscount = this.getDiscount(id);
		orginalDiscount.setIsActive(true);
		return discountRepository.save(orginalDiscount);
	}

	@Override
	public void makeAllDiscountUnActive() {
		List<Discount> discounts = discountRepository.findAll();
		for (Discount discount : discounts) {
			discount.setIsActive(false);
		}
		discountRepository.saveAll(discounts);
	}

	@Override
	public Discount findDiscountOn() {
		// TODO Auto-generated method stub
		 return discountRepository.findActiveDiscount();
	}

}
