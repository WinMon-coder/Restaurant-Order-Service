package com.Group3.OrderService.dto;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.Group3.OrderService.entity.BaseEntity;
import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.ToLowerCaseConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodDto extends BaseEntity {

	private int foodId;
	@JsonDeserialize(converter = ToLowerCaseConverter.class)
	private String foodName;
	private double foodPrice;
	private Category foodCategory;
	private Boolean isAvaliable;
	private String foodPhoto;
	
}
