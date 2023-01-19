package com.Group3.OrderService.entity;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Food extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int foodId;	
	
	//@JsonDeserialize(converter = ToLowerCaseConverter.class)
	@NotBlank(message = "Required")
	@Column(unique = true)	
	private String foodName;	
	
	private String foodPhoto;	
	
	@Min(value = 1, message = "Value should be greater then then equal to 1")
	private double foodPrice;
	
    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName ="categoryId", nullable = false)
	private Category foodCategory;
    
	@Column(columnDefinition = "boolean")
	@ColumnDefault("true")
	private Boolean isAvaliable;
	
//
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
//    private List<WishList> wishListList;
//
//
//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food")
//    private List<Cart> carts;
//



//	public void setEmail(String newValue)
//	{
//	    email = StringUtils.lowerCase(newValue);
//	}
	
	
}
