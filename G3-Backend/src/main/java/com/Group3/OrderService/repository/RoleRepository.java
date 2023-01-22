package com.Group3.OrderService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	public Role findByRoleName(String roleName);
	
}
