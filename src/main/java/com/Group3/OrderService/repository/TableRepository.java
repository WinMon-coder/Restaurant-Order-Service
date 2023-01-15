package com.Group3.OrderService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.Table;
@EnableJpaRepositories
@Repository
public interface TableRepository extends JpaRepository<Table, Integer>{
	public Table findByTableName(String tableName);
}
