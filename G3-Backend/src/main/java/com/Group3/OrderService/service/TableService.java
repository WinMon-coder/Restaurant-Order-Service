package com.Group3.OrderService.service;

import java.util.List;

import com.Group3.OrderService.entity.Table;

public interface TableService {
	public Table createTable(Table table);
	public List<Table> getAllTables();
	public Table getTable(int id);
	public Table updateTable(int id,Table table);
	public Boolean deleteTable(int id);
	public Table getByTableName(String tableName);

}
