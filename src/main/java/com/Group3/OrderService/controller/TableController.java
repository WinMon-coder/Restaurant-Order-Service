package com.Group3.OrderService.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RestController;

import com.Group3.OrderService.dto.FoodDto;
import com.Group3.OrderService.dto.TableDto;
import com.Group3.OrderService.entity.Food;
import com.Group3.OrderService.entity.Order;
import com.Group3.OrderService.entity.Table;
import com.Group3.OrderService.enumType.OrderStatus;
import com.Group3.OrderService.repository.OrderRepository;
import com.Group3.OrderService.service.OrderService;
import com.Group3.OrderService.service.TableService;

@RestController
@RequestMapping("/table")
public class TableController {
	
	@Autowired
	TableService tableService;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orderRepository;
	
	/***** GetAllTableList ******************************/
	@GetMapping(value="/tableName/{tableName}")
	public ResponseEntity<Boolean> findTableByName(@PathVariable("tableName") String tableName){
		
		Table table = tableService.getByTableName(tableName);
		
		if(table ==null) {
			return ResponseEntity.ok().body(false);
		}
		
		return ResponseEntity.ok().body(true);
	}

	@GetMapping(value = "/list")
	public ResponseEntity<List<TableDto>> getAllTables() {
		List<Table> tableList = tableService.getAllTables();
		List<TableDto>tableDtoList = new ArrayList<>();
		for (Table table : tableList) {
			tableDtoList.add(mapToDTO(table));
			
		}
		return ResponseEntity.ok().body(tableDtoList);

	}
	/***** GetAllTableList ******************************/
	
	@GetMapping(value = "/list_with_order")
	public ResponseEntity<List<TableDto>> getAllTablesWithOrder() {
		List<Table> tableList = tableService.getAllTables();
		List<TableDto>tableDtoList = new ArrayList<>();
		for (Table table : tableList) {
			TableDto tableDto = mapToDTO(table);			
			Order order = new Order();
			try {
				order=orderService.findByTableIdAndOrderStatus(table.getTableId(), "PROCESSING").get(0);
				tableDto.setBillTotal(order.getOrderTotal());
				tableDto.setOrder(orderService.getOrder(order.getOrderId()));
				
			} catch (Exception e) {
				
			}
			
			tableDtoList.add(tableDto);
			
		}
		return ResponseEntity.ok().body(tableDtoList);

	}
	/***** GetTable ******************************/
	@GetMapping(value = "get/{id}")
	public ResponseEntity<Table> getTable(@PathVariable int id) {
		Table table = tableService.getTable(id);
		if (table == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(table);

	}
	
	// convert entity to dto
		private TableDto mapToDTO(Table table) {
			return modelMapper.map(table, TableDto.class);
		}

	
}
