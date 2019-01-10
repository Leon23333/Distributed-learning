package com.xu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xu.entity.User;

@Service
public class MyService {
	@Reference
	UserService userService;
	
	public void saveUser() {
	}
	
	public List<User> findAllUser() {
		return userService.findAll();
	}

}
 