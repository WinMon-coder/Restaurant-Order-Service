package com.Group3.OrderService.service.Impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.enumType.OrderStatus;
import com.Group3.OrderService.repository.OrderRepository;
import com.Group3.OrderService.service.FoodService;
import com.Group3.OrderService.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	FoodService foodService;

	@Override
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order getOrder(int id) {
		return orderRepository.findById(id).orElseThrow();

	}

	@Transactional
	@Override
	public Order updateOrder(int id, Order order) {
		Order orderToEdit = getOrder(id);
		orderToEdit.setOrderTable(order.getOrderTable());
		orderToEdit.setOrderUser(order.getOrderUser());
		orderToEdit.setOrderItems(order.getOrderItems());
		orderToEdit.setOrderStatus(OrderStatus.PROCESSING);
		orderToEdit.setOrderTotal(order.getOrderTotal());
		return orderToEdit;
	}

	@Override
	public Boolean deleteOrder(int id) {
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null) {
			return false;
		}
		orderRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Order> findByTableIdAndOrderStatus(int tableId, String orderStatus) {		
		List<Order> orderStatusList =  orderRepository.findByOrderStatus(OrderStatus.PROCESSING);
		List<Order> orderWithTableID = orderStatusList
				  .stream()
				  .filter(o -> o.getOrderTable().getTableId() == tableId)
				  .collect(Collectors.toList());
		
		return orderWithTableID;
	}

	@Override
	public Order updateOrderStatus(int orderId) {
		 Order order = orderRepository.findById(orderId).orElse(null);
		 order.setOrderStatus(OrderStatus.DONE);		
		Order updateOrder =  orderRepository.save(order);	
		 
		 return updateOrder;
	}

//	@Transactional
//	public Order addFoodToOrder(int orderId, int foodId) {
//		// TODO Auto-generated method stub
//		Order order = getOrder(orderId);
//		FoodDto food = foodService.getFood(foodId);
//		order.addItem(mapToEntity(food));
//		return order;
//	}

//	@Transactional
//	public Order removeFoodFromOrder(int orderId, int foodId) {
//		// TODO Auto-generated method stub
//		Order order = getOrder(orderId);
//		FoodDto food = foodService.getFood(foodId);
//		order.removeItem(mapToEntity(food));
//		return order;
//	}

}
