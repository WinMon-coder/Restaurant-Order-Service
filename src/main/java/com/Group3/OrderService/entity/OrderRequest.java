package com.Group3.OrderService.entity;

import java.util.ArrayList;
import java.util.List;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.enumType.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public class OrderRequest {
		private int orderId;
		private User orderUser;
		private Table orderTable;
		private List<OrderItem>orderItems  = new ArrayList<>();
		private OrderStatus orderStatus;
		private double orderTotal;
		private double orderDiscountTotal;
			
	
}
