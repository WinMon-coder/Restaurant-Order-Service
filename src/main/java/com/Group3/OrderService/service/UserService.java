package com.Group3.OrderService.service;

import java.util.List;

import com.Group3.OrderService.dto.UserDto;
import com.Group3.OrderService.entity.User;


public interface UserService {
	
	 User register(User user);
	 List<User> getAllUsers();
	 User getUser(int id);
	 User updateProfile( int id,User user);
	 boolean deleteUser(int id);
	 User updateUserStatus(User user);	
		public List<String> getAllStatus();
	 User checkLoginUser(String gmail, String password);
	 void updatePwd(int id,String newPwd);
	
}
