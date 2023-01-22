package com.Group3.OrderService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.entity.OrderItem;

@EnableJpaRepositories
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

	@Query(value = "SELECT * FROM order_detail o WHERE o.order_id=order_id", nativeQuery = true)
	public List<OrderItem> findAllByOrderFood(int order_id);	
	public List<OrderItem> findByOrder(Order order);
	
	
	
	

}
