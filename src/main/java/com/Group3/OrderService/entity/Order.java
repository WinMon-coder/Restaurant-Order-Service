package com.Group3.OrderService.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

import com.Group3.OrderService.custom.validator.EnumStatusLimit;
import com.Group3.OrderService.enumType.OrderStatus;
import com.Group3.OrderService.enumType.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@javax.persistence.Table(name = "orderName")

public class Order extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderItem> orderItems;
	
	
	@ManyToOne
	@JoinColumn(name = "table_id", referencedColumnName = "tableId")
	private Table orderTable;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User orderUser;
	
	@Column(columnDefinition = "ENUM('DONE', 'PROCESSING') NOT NULL")
	@Enumerated(EnumType.STRING)	
	private OrderStatus orderStatus;
	
	private double orderTotal;
	private double orderDiscountTotal;

	
	
//	@OneToMany
//	@JoinColumn(name = "order_id",referencedColumnName = "orderId")
//	private List<Food> orderFood = new ArrayList<>();
		
//	@OneToMany
//	private List<Food> orderFood = new ArrayList<>();
//	public void addItem(Food food) {
//		orderFood.add(food);
//	}
//
//	public void removeItem(Food food) {
//		orderFood.remove(food);
//
//	}
}
