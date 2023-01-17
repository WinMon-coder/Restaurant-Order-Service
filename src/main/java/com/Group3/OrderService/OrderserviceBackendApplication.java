package com.Group3.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Group3.OrderService.entity.Role;
import com.Group3.OrderService.entity.User;
import com.Group3.OrderService.enumType.UserStatus;
import com.Group3.OrderService.repository.RoleRepository;
import com.Group3.OrderService.repository.UserRepository;
import com.Group3.OrderService.service.StorageService;

@SpringBootApplication
public class OrderserviceBackendApplication implements CommandLineRunner {
	@Autowired
	StorageService storageService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;

	@Value("${custom.delete.files}")
	private String deleteFiles;

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceBackendApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		if (ddlMode.equals("create")) {
			
			//Create Roles
			Role adminRole = new Role();
			adminRole.setRoleName("ADMIN");
			Role userRole = new Role();
			userRole.setRoleName("WAITER");
			roleRepository.save(adminRole);
			roleRepository.save(userRole);
			
			//Create Admin User
			User adminUser = new User();
			adminUser.setUserId(1);
			adminUser.setUserFullName("admin");
			adminUser.setUserEmail("admin@gmail.com");
			adminUser.setUserPassword(bCryptPasswordEncoder.encode("admin"));
			adminUser.setUserStatus(UserStatus.ACTIVE);
			adminUser.getUserRoles().add(adminRole);
			adminUser.setStartJoinDate(LocalDate.now());
			userRepository.save(adminUser);

		}
		if (deleteFiles.equals("true")) {
			storageService.clearAll();
		}

	}

}
