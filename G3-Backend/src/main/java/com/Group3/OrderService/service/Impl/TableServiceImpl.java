package com.Group3.OrderService.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Group3.OrderService.entity.Table;
import com.Group3.OrderService.repository.TableRepository;
import com.Group3.OrderService.service.TableService;


@Service
public class TableServiceImpl implements TableService {

	@Autowired
	TableRepository tableRepository;

	@Override
	public Table createTable(Table table) {
		return tableRepository.save(table);
	}

	@Override
	public List<Table> getAllTables() {
		return tableRepository.findAll();
	}

	@Override
	public Table getTable(int id) {
		return tableRepository.findById(id).orElse(null);
	}

	@Override
	public Table updateTable(int id, Table table) {
		Table orginalTable = this.getTable(id);
		if (orginalTable == null) {
			return null;
		}
		orginalTable.setTableId(id);
		orginalTable.setTableName(table.getTableName());
		orginalTable = tableRepository.save(orginalTable);

		return orginalTable;

	}

	@Override
	public Boolean deleteTable(int id) {
		Table Table = this.getTable(id);
		if (Table == null) {
			return false;
		}
		tableRepository.deleteById(id);
		return true;
	}

	@Override
	public Table getByTableName(String tableName) {
		// TODO Auto-generated method stub
		return tableRepository.findByTableName(tableName);
	}


}
