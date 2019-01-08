package com.xu.service;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xu.entity.User;

@Service
public class MyService {
	@Reference
	UserService userService;
	
	public User saveUser() {
		User user = new User();
		user.setUsername("zhangsan");
		return userService.saveUser(user);
	}

}
