//package com.Group3.OrderService.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Group3.OrderService.entity.Cart;
//import com.Group3.OrderService.entity.Category;
//import com.Group3.OrderService.entity.Food;
//import com.Group3.OrderService.repository.FoodRepository;
//import com.Group3.OrderService.service.CartService;
//import com.Group3.OrderService.service.FoodService;
//
//@RestController
//@RequestMapping("/cart")
//public class CatController {
//	@Autowired
//	CartService cartService;
//	@Autowired 
//	FoodRepository foodRepository;
//	
//	/***** GetAllCategoryList ******************************/
//	@GetMapping(value = "/list")
//	public ResponseEntity<List<Cart>> getAllCarts() {
//		List<Cart> carts = cartService.getAllCarts();
//		return ResponseEntity.ok().body(carts);
//
//	}
//	/***** GetCategory ******************************/
//	@GetMapping(value = "/{id}")
//	public ResponseEntity<Cart> getCart(@PathVariable int id) {
//		Cart cart = cartService.getCart(id);
//		if (cart == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().body(cart);
//
//	}
//	
//	/***** UpdateCategory ******************************/
//	@PutMapping(value = "/update/{id}")
//	public ResponseEntity<Cart> updateCart(@PathVariable int id, @Valid @RequestBody Cart cart) {
//		Cart updateCart = cartService.updateCart(id, cart);
//		if (updateCart == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().body(updateCart);
//	}
//
//	/***** DeleteCategory ******************************/
//	@DeleteMapping(value = "/delete/{id}")
//	public ResponseEntity<?> deleteCart(@PathVariable int id) {
//		Cart cart = cartService.getCart(id);
//		if (cart == null) {
//			return ResponseEntity.notFound().build();
//		}
//		boolean isDeleted = cartService.deleteCart(id);
//
//		if (!isDeleted) {
//			return ResponseEntity.notFound().build();
//
//		}
//		return ResponseEntity.ok().build();
//
//	}
//
//}
