package com.xu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xu.entity.User;
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
	
//	@RequestMapping("/findAll")
//	private List<User> findAll(){
//		return seckillService.findAll();
//	}
	
}
  