//package com.Group3.OrderService.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//
//import com.Group3.OrderService.dto.FoodDto;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class Cart {
//	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int cartId;
//
//	@ManyToOne
//    @JoinColumn(name = "food_id", referencedColumnName = "foodId")
//    private Food food;
//
////    @JsonIgnore
////    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
////    @JoinColumn(nullable = false, name = "user_id")
////    private User user;
//
//
//    private int quantity;
//
//}
