package com.xu.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private String port;

	@Bean
	RedissonClient redissonSentinel() {
		//支持单机，主从，哨兵，集群等模式
		//此为哨兵模式
		Config config = new Config();
//		config.useSentinelServers()
//				.setMasterName("mymaster")
//				.addSentinelAddress("redis://192.168.1.1:26379")
//				.setPassword("123456");
		config.useSingleServer().setAddress("redis://" + host + ":" + port);
		return Redisson.create(config);
	}
}
