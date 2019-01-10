package com.xu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xu.entity.User;
import com.xu.service.MyService;

@RestController
public class UserController {
	@Autowired
	private MyService myService;

	@GetMapping("/save")
	public void save() {
		myService.saveUser();
	}
	
	@GetMapping("/findAll")
	public List<User> findAll() {
		return myService.findAllUser();
	}
}
