package com.Group3.OrderService.dto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.Group3.OrderService.custom.validator.EnumStatusLimit;
import com.Group3.OrderService.entity.BaseEntity;
import com.Group3.OrderService.entity.Role;
import com.Group3.OrderService.enumType.UserStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto extends BaseEntity{

	private int userId;
	private String userName;	
	private String userEmail;	
	private String userPassword;
	@EnumStatusLimit(regexp = "ACTIVE|REST")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@NotNull(message = "User role can't be null")
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Role> userRoles = new ArrayList<>();

	private String userPhoto;
	private String userPhone;
	private String userAddress;
	private OffsetDateTime startJoinDate;	
	private OffsetDateTime lastJoinDate;
}
