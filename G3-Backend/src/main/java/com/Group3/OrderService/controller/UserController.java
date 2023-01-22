package com.Group3.OrderService.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.ChangePassword;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.entity.LoginRequest;
import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.entity.OrderItem;
import com.Group3.OrderService.entity.OrderRequest;
import com.Group3.OrderService.entity.User;
import com.Group3.OrderService.enumType.OrderStatus;
import com.Group3.OrderService.repository.FoodRepository;
import com.Group3.OrderService.repository.OrderRepository;
import com.Group3.OrderService.repository.UserRepository;
import com.Group3.OrderService.service.OrderItemService;
import com.Group3.OrderService.service.OrderService;
import com.Group3.OrderService.service.TableService;
import com.Group3.OrderService.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	TableService tableService;
	@Autowired
	UserRepository userRepository;
	@Autowired 
	FoodRepository foodRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	/*
	 * -----------------------------------START BY ORDER
	 * -----------------------------------
	 */
	/***** UpdateOrderStatus ******************************/
	@PutMapping(value = "/update/order_status")
	public ResponseEntity<Order>updateOrder(@RequestBody Order order) {
		
		Order updateOrderStatus = orderService.updateOrderStatus(order.getOrderId());
		
		return new ResponseEntity<Order>(updateOrderStatus,HttpStatus.OK);
	}
	/***** GetOrderByStatus ******************************/
	@GetMapping("/order")
	public ResponseEntity<List<Order>> getOrderByStatus(@RequestParam int tableId ,@RequestParam String orderStatus) {
		Order order = new Order();
		List<Order> orderList = orderService.findByTableIdAndOrderStatus(tableId, orderStatus);
		System.out.println(tableId);
		System.out.println(orderList.size());
		return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
	}
		
	/***** CreateOrder ******************************/
	@PostMapping("/order/create")
	public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
		Order requestOrder = new Order();
		requestOrder.setOrderUser(orderRequest.getOrderUser());		
		requestOrder.setOrderTable(orderRequest.getOrderTable());
		requestOrder.setOrderStatus(OrderStatus.PROCESSING);
		requestOrder.setOrderTotal(orderRequest.getOrderTotal());	
		requestOrder.setOrderDiscountTotal(orderRequest.getOrderDiscountTotal());
		Order newOrder = orderService.createOrder(requestOrder);
		for (int i = 0; i < orderRequest.getOrderItems().size(); i++) {
			Food food = orderRequest.getOrderItems().get(i).getFood();
				OrderItem orderItem = new OrderItem();
				orderItem.setFood(food);			
				orderItem.setOrder(newOrder);		
				orderItem.setAmount(orderItem.getFood().getFoodPrice());
				orderItem.setQuantity(orderRequest.getOrderItems().get(i).getQuantity());	
				
				orderItemService.createOrderItem(orderItem);
					
			}
		
		return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
	}
	
	/***** DeleteOrderItem ******************************/
	@DeleteMapping(value = "/order_item/{id}")
	public ResponseEntity<?> deleteOrderItem(@PathVariable int id) {
		OrderItem orderItem = orderItemService.getOrderItem(id);
		if (orderItem == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = orderItemService.deleteOrderItem(id);

		if (!isDeleted) {
			return ResponseEntity.notFound().build();

		}else
		return ResponseEntity.ok().build();

	}
	/***** UpdateCategory ******************************/
	@PutMapping(value = "/order/update/{id}")
	public ResponseEntity<Order> updateCategory(@PathVariable int id, @Valid @RequestBody Order order) {
		Order oldOlder = orderService.getOrder(id);
		orderItemService.deleteOrderItem(oldOlder.getOrderId());
		
		
		Order updateOrder = orderService.updateOrder(id, order);
		updateOrder.setOrderDiscountTotal(order.getOrderDiscountTotal());
		for (int i = 0; i < updateOrder.getOrderItems().size(); i++) {		
			Food food = order.getOrderItems().get(i).getFood();
				OrderItem orderItem = new OrderItem();
				orderItem.setFood(food);			
				orderItem.setOrder(updateOrder);		
				orderItem.setAmount(orderItem.getFood().getFoodPrice());
				orderItem.setQuantity(order.getOrderItems().get(i).getQuantity());	
				orderItem.setOrderItemId(order.getOrderItems().get(i).getOrderItemId());
				
				orderItemService.createOrderItem(orderItem);
					
			}
		return ResponseEntity.ok().body(updateOrder);
	}

	/***** GetAllOrderList ******************************/
	@GetMapping(value = "/order/list")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> n = orderService.getAllOrders();
		System.out.println(n.size());
		return ResponseEntity.ok().body(n);

	}
	
	/*
	 * -----------------------------------END BY ORDER
	 * -----------------------------------
	 */
	
	/*
	 * -----------------------------------START BY REGISTER/LOGIN
	 * -----------------------------------
	 */
	

	/***** Register ******************************/
	@PostMapping("/register")
	public ResponseEntity<?> save(@Valid @RequestBody User user) {
		User createdUser = userService.register(user);
		if (createdUser == null) {
			return ResponseEntity.badRequest().body("User with the same email already exists!");
		}
		return ResponseEntity.ok().body(createdUser);
	}

	/***** Login ******************************/
	@PostMapping("/login")
	public ResponseEntity<User> login(@Valid @RequestBody LoginRequest loginRequest) {
		User user = userService.checkLoginUser(loginRequest.getUserEmail(), loginRequest.getUserPassword());
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(user);
	}

	/***** Profile ******************************/
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@RequestParam int id) {
		User user = userService.getUser(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}

	/***** UpdateProfile ******************************/
	@PutMapping("/profile/update/{id}")
	public ResponseEntity<User> updateUserProfile(@PathVariable int id, @RequestBody User user) {
		if (user.getUserId() <= 0) {
			return ResponseEntity.badRequest().build();
		}
		User updateProfile = userService.updateProfile(id, user);

		if (updateProfile == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateProfile);
	}

	/***** ChangePassword ******************************/
	@PostMapping("/change_password")
	public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePassword changePwd) throws IOException {
		User user = userService.getUser(changePwd.getOriId());
		if (user == null) {
			return new ResponseEntity<Object>(new Exception("Something wrong"), HttpStatus.CONFLICT);
		}
		if (!changePwd.getConNewPwd().equals(changePwd.getNewPwd())) {
			return new ResponseEntity<Object>(new Exception("Confirm Password does not match"), HttpStatus.CONFLICT);
		}
		if (!bCryptPasswordEncoder.matches(changePwd.getCurrentPwd(), user.getUserPassword())) {
			return new ResponseEntity<Object>(new Exception("Current Password does not match"), HttpStatus.CONFLICT);
		}
		userService.updatePwd(user.getUserId(), changePwd.getNewPwd());
		return ResponseEntity.ok().body(user);
	}
	
	/*
	 * -----------------------------------END BY REGISTER/LOGIN
	 * -----------------------------------
	 */

	// convert dto to entity
	private Food mapToEntity(FoodDto foodDto) {

		return modelMapper.map(foodDto, Food.class);

	}

	// convert entity to dto
	private FoodDto mapToDTO(Food food) {
		return modelMapper.map(food, FoodDto.class);

	}

}