package com.Group3.OrderService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	/***** GetCategory ******************************/
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> getOrder(@PathVariable int id) {
		
		Order order = orderService.getOrder(id);	
		
//		List<OrderDetail> orderDetail = orderDetailService.findByOrder(order);
//		order.setOrderDetail(orderDetail);		
		return ResponseEntity.ok().body(order);

	}
	
}
