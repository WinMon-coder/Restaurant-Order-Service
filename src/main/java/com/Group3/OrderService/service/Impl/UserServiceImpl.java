package com.Group3.OrderService.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.dto.UserDto;
import com.Group3.OrderService.entity.Role;
import com.Group3.OrderService.entity.User;
import com.Group3.OrderService.enumType.UserStatus;
import com.Group3.OrderService.repository.RoleRepository;
import com.Group3.OrderService.repository.UserRepository;
import com.Group3.OrderService.service.UserService;
@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@Override
	public User register(User user) {
		// Check IF user with same gmail exists
				User tempGmailCheckUser = userRepository.findByUserEmail(user.getUserEmail());
				if (tempGmailCheckUser != null) {
					return null;
				}		
				user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
				user.setUserStatus(UserStatus.ACTIVE);
				//Add Role
				List<Role> roleList = roleRepository.findAll();
				user.getUserRoles().add(roleList.get(1));
				user.setStartJoinDate(LocalDate.now());
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(int id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User updateProfile(int id, User user) {
		User orginalUser = this.getUser(id);
		if (orginalUser != null) {
			orginalUser.setUserId(id);
			orginalUser.setUserFullName(user.getUserFullName());
			orginalUser.setUserEmail(user.getUserEmail());
			orginalUser.setUserPhoto(user.getUserPhoto());
			orginalUser.setUserPhone(user.getUserPhone());
			orginalUser.setUserAddress(user.getUserAddress());
			orginalUser.setUpdatedAt(OffsetDateTime.now());
			orginalUser = userRepository.save(orginalUser);
		}
		return orginalUser;
	}

	@Override
	public List<String> getAllStatus() {
		List<String> userStatusList = new ArrayList<>();
		for (UserStatus status : Arrays.asList(UserStatus.values())) {
			userStatusList.add(status.toString());
		}
		return userStatusList;
	}

	@Override
	public boolean deleteUser(int id) {
		User user = this.getUser(id);
		if (user == null) {
			return false;
		}
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public User checkLoginUser(String email, String password) {
		User user = userRepository.findByUserEmail(email);
		if (user == null) {
			return null;
		}
		if (!bCryptPasswordEncoder.matches(password, user.getUserPassword())) {
			return null;
		}
		return userRepository.save(user);
	}

	@Override
	public User updateUserStatus(User user) {
		User original = userRepository.findById(user.getUserId()).orElse(null);
		if(original != null) {
			original.setUserId(user.getUserId());
			original.setUserStatus(user.getUserStatus());
			original.setStartJoinDate(user.getStartJoinDate());
			original.setLastJoinDate(user.getLastJoinDate());
			original.setUpdatedAt(OffsetDateTime.now());
			original = userRepository.save(original);
		}
		
		
		return original;
	}

	@Override
	public void updatePwd(int id, String newPwd) {

		User toUpdateUserPwd = getUser(id);
		if (toUpdateUserPwd != null) {
			toUpdateUserPwd.setUserPassword(bCryptPasswordEncoder.encode(newPwd));
			userRepository.save(toUpdateUserPwd);
		}

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		        return userRepository.findByUserEmail(email);
	}
	
}
