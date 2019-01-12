package com.xu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xu.service.SeckillService;

@RestController
public class SeckillController {
	@Autowired
	private SeckillService seckillService;

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
	
//	@RequestMapping("/findAll")
//	private List<User> findAll(){
//		return seckillService.findAll();
//	}
	
}
  