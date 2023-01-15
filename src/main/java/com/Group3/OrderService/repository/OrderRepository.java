package com.Group3.OrderService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.entity.OrderItem;
import com.Group3.OrderService.enumType.OrderStatus;
@EnableJpaRepositories
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	
	
	public List<Order> findByOrderStatus(OrderStatus orderStatus);
	//public Order deleteOldItem(Order order);

}
