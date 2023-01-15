package com.Group3.OrderService.dto;

import com.Group3.OrderService.entity.BaseEntity;
import com.Group3.OrderService.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDto extends BaseEntity{
	private int tableId;
	private String tableName;
	private double billTotal = 0;
	private Order order= new Order();
	

}
