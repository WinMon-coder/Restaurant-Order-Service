package com.Group3.OrderService.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Group3.OrderService.custom.validator.EnumStatusLimit;
import com.Group3.OrderService.enumType.UserStatus;
import com.Group3.OrderService.security.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User extends BaseEntity implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(length = 45, nullable = false)
	private String userFullName;
	
	@Column(length = 100, nullable = false, unique = true)
	private String userEmail;
	
	@Column(length = 10, nullable = false)
	private String userPassword;

	@Column(columnDefinition = "ENUM('ACTIVE', 'REST') NOT NULL")
	@EnumStatusLimit(regexp = "ACTIVE|REST")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	 @JsonIgnore
	 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	 @OneToMany(mappedBy = "orderUser",
	            fetch = FetchType.LAZY)
	    private List<Order> orders;

	 @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	 @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	    @JoinTable(name = "user_roles",
	               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
	               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"))
	    private Set<Role> userRoles = new HashSet<>();

	private String userPhoto;
	private String userPhone;
	private String userAddress;
	private LocalDate startJoinDate;
	private LocalDate lastJoinDate;
	
//	@Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return userRoles
//        		.stream()
//        		.map(r->new SimpleGrantedAuthority(r.getRoleName()))
//        		.collect(Collectors.toSet());
//    }
	@Override
	public String getPassword() {
		return userPassword;
	}
	@Override
	public String getUsername() {
		return userFullName;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userRoles
        		.stream()
        		.map(r->new SimpleGrantedAuthority(r.getRoleName()))
        		.collect(Collectors.toSet());
	}
	
//	//map roles
//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
//       return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//    }
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//		 return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
//	//    
//    }
}
