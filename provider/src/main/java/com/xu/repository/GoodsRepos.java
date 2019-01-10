package com.xu.repository;

import java.util.List;

import com.xu.entity.Goods;

public interface GoodsRepos {
	List<Goods> findAll();
	
	int insert(Goods goods);
	
	int update(Goods goods);
	
	int updateAmount(Integer amount);
}
