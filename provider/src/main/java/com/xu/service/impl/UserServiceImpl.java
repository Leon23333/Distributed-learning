package com.xu.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.xu.entity.User;
import com.xu.service.UserService;

@Service
@Component
public class UserServiceImpl implements UserService {

	@Override
	public User saveUser(User user) {
		user.setId("1");
		return user;
	}

}
