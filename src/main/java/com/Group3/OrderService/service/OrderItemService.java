package com.Group3.OrderService.service;


import com.Group3.OrderService.entity.OrderItem;

public interface OrderItemService {
	public OrderItem createOrderItem(OrderItem orderItem);	
	public Boolean deleteOrderItem(int id);
	public OrderItem getOrderItem(int id);
}
