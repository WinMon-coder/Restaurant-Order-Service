package com.Group3.OrderService.service;

import java.util.List;

import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.enumType.OrderStatus;

public interface OrderService {
	public Order createOrder(Order order);

	public List<Order> getAllOrders();

	public Order getOrder(int id);

	public Order updateOrder(int id, Order order);

	public Boolean deleteOrder(int id);
	
	public List<Order> findByTableIdAndOrderStatus(int tableId,String orderStatus);
	
	public Order updateOrderStatus(int orderId);

}
