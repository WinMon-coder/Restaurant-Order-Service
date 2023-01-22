package com.Group3.OrderService.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.entity.Category;
import com.Group3.OrderService.entity.Discount;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.entity.Table;
import com.Group3.OrderService.entity.User;
import com.Group3.OrderService.service.CategoryService;
import com.Group3.OrderService.service.DiscountService;
import com.Group3.OrderService.service.FoodService;
import com.Group3.OrderService.service.StorageService;
import com.Group3.OrderService.service.TableService;
import com.Group3.OrderService.service.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	UserService userService;
	@Autowired
	StorageService storageService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	FoodService foodService;
	@Autowired
	DiscountService discountService;
	@Autowired
	TableService tableService;

	/*
	 * ----------------------------------- START BY DISCOUNT
	 * -----------------------------------
	 */
	/***** CreateDiscount ******************************/
		
	@PostMapping("/discount/create")
	public ResponseEntity<Discount> createDiscount(@Valid @RequestBody Discount discount) {
		Discount newDiscount = discountService.createDiscount(discount);
		return new ResponseEntity<Discount>(newDiscount, HttpStatus.OK);

	}

	/***** UpdateDiscount ******************************/
	@PutMapping(value = "/discount/update/{id}")
	public ResponseEntity<Discount> updateDiscount(@PathVariable int id, @Valid @RequestBody Discount discount) {
		Discount updateDiscount = discountService.updateDiscount(id, discount);
		if (updateDiscount == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateDiscount);
	}
	/***** UpdateDiscount Active ******************************/
	@PutMapping(value = "/discount/update_active/{id}")
	public ResponseEntity<Discount> updateDiscountStatusOn(@PathVariable int id, @RequestBody Discount discount) {
		Discount updateDiscountStatus = discountService.updateActiveDiscount(discount.getDiscountId(),discount);
		return ResponseEntity.ok().body(updateDiscountStatus);
	}
	@PutMapping(value = "/discount/update_unactive")
	public ResponseEntity<?> updateAllDiscountStatusOff() {

		 discountService.makeAllDiscountUnActive();
		return ResponseEntity.ok().build();
	}
	/*
	 * ----------------------------------- END BY DISCOUNT
	 * -----------------------------------
	 */
	/*
	 * ----------------------------------- START BY CATEGORY
	 * -----------------------------------
	 */
	/***** CreateCategory ******************************/
		
	@PostMapping("/category/create")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
		Category newCategory = categoryService.createCategory(category);
		return new ResponseEntity<Category>(newCategory, HttpStatus.OK);

	}

	/***** UpdateCategory ******************************/
	@PutMapping(value = "/category/update/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable int id, @Valid @RequestBody Category category) {
		Category updateCategory = categoryService.updateCategory(id, category);
		if (updateCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateCategory);
	}

	/***** DeleteCategory ******************************/
	@DeleteMapping(value = "/category/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {
		Category category = categoryService.getCategory(id);
		if (category == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = categoryService.deleteCategory(id);

		if (!isDeleted) {
			return ResponseEntity.notFound().build();

		}
		// Delete Photo
		storageService.delete(category.getCategoryPhoto());

		return ResponseEntity.ok().build();

	}

	/***** DeleteCategory ******************************/
	@DeleteMapping(value = "/discount/delete/{id}")
	public ResponseEntity<?> deleteDiscount(@PathVariable int id) {
		Discount discount = discountService.getDiscount(id);
		if (discount == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = discountService.deleteDiscount(id);

		if (!isDeleted) {
			return ResponseEntity.notFound().build();

		}
		
		return ResponseEntity.ok().build();

	}
	/*
	 * ----------------------------------- END BY CATEGORY
	 * -----------------------------------
	 */

	/*
	 * ----------------------------------- START BY USER
	 * -----------------------------------
	 */
	/***** GetAllUserList ******************************/
	@GetMapping("/user/list")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userService.getAllUsers();
		return ResponseEntity.ok().body(userList);
	}

	/***** UpdateUserStatus ******************************/
	@PutMapping(value = "/update/user_status/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user) {

		User update_user = userService.updateUserStatus(user);

		return new ResponseEntity<User>(update_user, HttpStatus.OK);
	}

	/***** GetAllUserStatus ******************************/
	@GetMapping("/user_status")
	public List<String> listUserStatus() {
		return userService.getAllStatus();
	}

	/***** DeleteUser ******************************/
	@DeleteMapping(value = "/user/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id) {
		User user = userService.getUser(id);
		if (user == null) {
			return ResponseEntity.badRequest().body("User cannot be null");
		}
		boolean isDeleted = userService.deleteUser(id);
		if (!isDeleted) {
			return ResponseEntity.notFound().build();
		}
		// Delete Photo
				//storageService.delete(user.getUserPhoto());

		return ResponseEntity.ok().build();

	}
	/*
	 * ----------------------------------- END BY USER
	 * -----------------------------------
	 */

	/*
	 * ----------------------------------- START BY FILE
	 * -----------------------------------
	 */
	/***** CreateFile ******************************/
	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType) {
		String filePath = storageService.create(file, fileType);
		if (filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	}

	/***** UpdateFile ******************************/
	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType, @RequestParam("filePath") String filePath) {
		String newFilePath = storageService.update(file, fileType, filePath);
		if (newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}
	/*
	 * ----------------------------------- END BY FILE
	 * -----------------------------------
	 */

	/*
	 * ----------------------------------- START BY FOOD
	 * -----------------------------------
	 */
	/***** CreateFood ******************************/
	@PostMapping("/food/create")
	public ResponseEntity<Food> createFood(@Valid @RequestBody FoodDto foodDto) {
		return ResponseEntity.ok().body(foodService.createFood(foodDto));

	}

	/***** UpdateFood ******************************/
	@PutMapping(value = "/food/update/{id}")
	public ResponseEntity<Food> updateFood(@PathVariable int id, @Valid @RequestBody FoodDto foodDto) {
		Food updatefood = foodService.updateFood(id, foodDto);
		if (updatefood == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatefood);
	}

	/***** DeleteFood ******************************/
	@DeleteMapping(value = "/food/delete/{id}")
	public ResponseEntity<?> deleteFood(@PathVariable int id) {
		FoodDto foodDto = foodService.getFood(id);
		if (foodDto == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = foodService.deleteFood(id);

		if (!isDeleted) {
			return ResponseEntity.notFound().build();

		}
		// Delete Photo
				storageService.delete(foodDto.getFoodPhoto());

		return ResponseEntity.ok().build();

	}
	/*----------------------------------- END BY FOOD ----------------------------------- */

	/*
	 * -----------------------------------START BY TABLE
	 * -----------------------------------
	 */

	/***** CreateTable ******************************/
	@PostMapping("/table/create")
	public ResponseEntity<Table> createTable(@Valid @RequestBody Table table) {
		Table newTable = tableService.createTable(table);
		return new ResponseEntity<Table>(newTable, HttpStatus.ACCEPTED);

	}

	/***** UpdateTable ******************************/
	@PutMapping(value = "/table/update/{id}")
	public ResponseEntity<Table> updateTable(@PathVariable int id, @Valid @RequestBody Table table) {
		Table updateTable = tableService.updateTable(id, table);
		if (updateTable == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateTable);
	}

	/***** DeleteTable ******************************/
	@DeleteMapping(value = "/table/delete/{id}")
	public ResponseEntity<?> deleteTable(@PathVariable int id) {
		Table table = tableService.getTable(id);
		if (table == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = tableService.deleteTable(id);

		if (!isDeleted) {
			return ResponseEntity.notFound().build();

		}
		return ResponseEntity.ok().build();

	}
	/*
	 * -----------------------------------END BY TABLE
	 * -----------------------------------
	 */

}
