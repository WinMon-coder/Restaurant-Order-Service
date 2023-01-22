package com.Group3.OrderService.service;

import java.util.List;

import com.Group3.OrderService.entity.Discount;

public interface DiscountService {

	public Discount createDiscount(Discount discount);
	public List<Discount> getAllDiscounts();
	public Discount getDiscount(int id);
	public Discount updateDiscount(int id,Discount discount);
	public Boolean deleteDiscount(int id);
	
	public Discount getByDiscountName(String discountName);
	
	public Discount updateActiveDiscount(int id, Discount discount);
	public void makeAllDiscountUnActive();
	public Discount findDiscountOn();

}
