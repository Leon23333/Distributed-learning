package com.xu.controller;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xu.service.SeckillService;

@RestController
public class SeckillController {
	@Autowired
	private SeckillService seckillService;

	private Lock lock = new ReentrantLock();

	@RequestMapping("/createWrongOrder/{stockId}")
	public Long createWrongOrder(@PathVariable Long stockId) {
		Long orderId = null;
		try {
			orderId = seckillService.createWrongOrder(stockId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;
	}

	@RequestMapping("/createOrderOptimistic/{stockId}")
	public Long createOrderOptimistic(@PathVariable Long stockId) {
		Long orderId = null;
		try {
			orderId = seckillService.createOrderOptimistic(stockId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;
	}

	// 使用ReentrantLock
	@RequestMapping("/createOrderLock/{stockId}")
	public Long createOrderLock(@PathVariable Long stockId) {
		Long orderId = null;
		lock.lock();
		try {
			orderId = seckillService.createWrongOrder(stockId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return orderId;
	}

	// 使用synchronized
	@RequestMapping("/createOrderSynchronized/{stockId}")
	public Long createOrderSynchronized(@PathVariable Long stockId) {
		Long orderId = null;
		synchronized (this) {
			try {
				orderId = seckillService.createWrongOrder(stockId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderId;
	}

	//分布式锁
	@RequestMapping("/createOrderRedisson/{stockId}")
	public Long createOrderRedisson(@PathVariable Long stockId) {
		Long orderId = null;
		synchronized (this) {
			try {
				orderId = seckillService.createOrderRedisson(stockId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderId;
	}
}
