package com.xu.utils;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * 这种方式好像并不能解决超时问题，当线程A获取到一把有效时间为5秒的锁，但执行业务逻辑需要7秒钟，5秒钟后锁过期（即此时锁可被其它线程获取），
 * 此时线程A仍在执行，线程B获取到锁开始处理业务(假设需要4秒钟)，在B还未执行完时，A执行完成进行释放锁操作，此时会把B的锁释放掉。
 * 产生这样问题的原因是各线程在释放锁时，没有判断锁是否是自己持有的
 * @author xzy
 *
 */
@Component
public class RedisReentrantLock {
	// 用ThreadLocal保存获得锁的次数，是属于每个线程单独所有的
	private ThreadLocal<Integer> local = new ThreadLocal<>();

	private Jedis jedis = new Jedis();

	private boolean doLock(String key) {
		// 结果不为null表示设置成功，即获取到锁
		return jedis.set(key, "", "nx", "ex", 5L) != null;
	}

	private void doUnlock(String key) {
		jedis.del(key);
	}

	public boolean lock(String key) {
		// 次数不为空表示之前已经获取到了，次数加以即可
		Integer lockCount = local.get();
		if (lockCount != null) {
			local.set(lockCount + 1);
			return true;
		}
		// 否则表示是初次获取，未获取到则返回false,获取到则计数为1并返回true
		if (!doLock(key)) {
			return false;
		}
		local.set(1);
		return true;
	}

	public boolean unlock(String key) {
		// 次数为空表示没有持有锁，返回false
		Integer lockCount = local.get();
		if (lockCount == null) {
			return false;
		}
		// 否则先减1再判断，如果大于0说明不是最后一把锁，更新次数即可
		if (--lockCount > 0) {
			local.set(lockCount);
		}
		// 否则说明是最后一把锁，删除锁并将次数置空
		else {
			doUnlock(key);
			local.set(null);
		}
		return true;
	}
}
