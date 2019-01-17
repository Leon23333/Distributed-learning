package com.xu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.xu.entity.User;
import com.xu.service.OrderService;

@Service
public class SeckillService{
	
	@Reference
	OrderService orderService;
	
	public Long createWrongOrder(Long stockId) throws Exception {
		return orderService.createWrongOrder(stockId);
	}
	
	public Long createOrderOptimistic(Long stockId) throws Exception {
		return orderService.createOrderOptimistic(stockId);
	}
	
	public Long createOrderRedisson(Long stockId) throws Exception {
		return orderService.createOrderRedisson(stockId);
	}
	
	public Long createOrderRedisReenrantLock(Long stockId) throws Exception {
		return orderService.createOrderRedisReenrantLock(stockId);
	}
}
