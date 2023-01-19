package com.Group3.OrderService.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Group3.OrderService.entity.Discount;

@EnableJpaRepositories
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
	@Transactional
	@Modifying
	
	@Query(value = "UPDATE Discount d set isActive =1 where d.discountId = :id", nativeQuery = true)	
	public Discount makeActiveDiscount(int id);
	
	@Query("SELECT d FROM Discount d WHERE d.isActive = 1")
	public Discount findActiveDiscount();
	
	public Discount findByDiscountName(String discountName);

}
