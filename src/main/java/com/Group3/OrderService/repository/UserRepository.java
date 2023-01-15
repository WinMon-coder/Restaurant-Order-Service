package com.Group3.OrderService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	// @Query("select s from user u where u.user_email = :user_email")
	public User findByUserEmail(String userGmail);

}
