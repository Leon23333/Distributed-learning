package com.xu.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xu.entity.StockOrder;

@Repository
public interface OrderRepos {
	List<StockOrder> getAll();
	
	StockOrder getById(Long id);
	
	Long insert(StockOrder order);
	
	int updateById(Long id);
}
