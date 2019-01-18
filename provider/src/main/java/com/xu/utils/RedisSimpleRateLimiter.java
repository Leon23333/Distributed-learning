package com.xu.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

@Component
public class RedisSimpleRateLimiter {
	private Jedis jedis = new Jedis();

/**
 * 1、添加此次请求到时间窗口，使用当前时间戳作为score
 * 2、清除一下时间窗口之外的数据。
 * 3、统计时间窗口内的数量。如果大于等于限制次数，直接返回false，否则返回true
 * @param key
 * @param value
 * @param period
 * @param maxCount
 * @return
 * @throws IOException
 */
	public boolean isActionAllowed(String key, String value, int period, int maxCount) throws IOException {
		Long now = System.currentTimeMillis();
		Pipeline pipe = jedis.pipelined();
		pipe.multi();
		pipe.zadd(key, now, value);
		pipe.zremrangeByScore(key, 0, now - period * 1000);
		Response<Long> count = pipe.zcard(key);
		pipe.expire(key, period + 1);
		pipe.exec();
		pipe.close();
		return count.get() <= maxCount;
	}
}
