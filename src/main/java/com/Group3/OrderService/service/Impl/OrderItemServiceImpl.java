package com.Group3.OrderService.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.entity.OrderItem;
import com.Group3.OrderService.repository.OrderItemRepository;
import com.Group3.OrderService.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService{
@Autowired
OrderItemRepository orderItemRepository;

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}
	@Override
	public OrderItem getOrderItem(int id) {
		return orderItemRepository.findById(id).orElse(null);
	}

	@Override
	public Boolean deleteOrderItem(int id) {
		OrderItem orderItem = this.getOrderItem(id);
		if (orderItem == null) {
			return false;
		}
		orderItemRepository.deleteById(id);
		return true;
	}

	
}