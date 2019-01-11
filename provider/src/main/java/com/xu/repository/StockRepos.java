package com.xu.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xu.entity.Stock;

@Repository
public interface StockRepos {
	List<Stock> getAll();
	
	Stock getById(Long id);
	
	Long insert(Stock stock);
	
	int updateById(Stock stock);
}
