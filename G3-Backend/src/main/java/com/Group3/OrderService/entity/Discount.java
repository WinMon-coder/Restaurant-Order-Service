package com.Group3.OrderService.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Discount extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int discountId;
	
	@Max(value = 100, message = "Value should be less then then equal to 100")
	@Min(value = 1, message = "Value should be greater then then equal to 1")
	
	private int discountPercentage;

	@NotBlank(message = "Required")
	@Column(unique = true)
	private String discountName;
	
	private int mininumOrderAmount;
	
	
	//@ColumnDefault("false")
	private Boolean isActive;

}