package com.xu.service;

public interface OrderService {
	/*
	 * 创建会有超卖问题的订单
	 */
	Long createWrongOrder(Long stockId) throws Exception;
	
	/*
	 * 乐观锁
	 */
	Long createOrderOptimistic(Long stockId) throws Exception;
}