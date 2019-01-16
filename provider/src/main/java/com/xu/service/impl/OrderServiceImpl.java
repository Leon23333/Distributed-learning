package com.xu.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.xu.entity.StockOrder;
import com.xu.aop.ServiceLimit;
import com.xu.config.RedisKeyPrefix;
import com.xu.entity.Stock;
import com.xu.repository.OrderRepos;
import com.xu.repository.StockRepos;
import com.xu.service.OrderService;

@Transactional(rollbackFor = Exception.class)
@Service(interfaceName = "com.xu.service.OrderService")
@Component
public class OrderServiceImpl implements OrderService {
	@Autowired
	private StockRepos stockRepos;
	@Autowired
	private OrderRepos orderRepos;
	@Resource
	private RedisTemplate<String, Stock> redisTemplate;
	@Autowired
	private RedissonClient redissonClient;

	@Override
	@ServiceLimit
	public Long createWrongOrder(Long stockId) throws Exception {
		// 检查库存
		Stock stock = checkStock(stockId);

		// 扣减库存
		saleStock(stock);

		// 生成订单
		Long orderId = createOrder(stock);
		return orderId;
	}

	@Override
	@ServiceLimit
	public Long createOrderOptimistic(Long stockId) throws Exception {
		// 检查库存
		Stock stock = checkStock(stockId);

		// 扣减库存
		saleStockOptimistic(stock);

		// 生成订单
		Long orderId = createOrder(stock);
		return orderId;
	}

	private Stock checkStock(Long stockId) {
		String key = RedisKeyPrefix.STOCK + stockId;
		Stock stock = redisTemplate.opsForValue().get(key);
		if (stock == null) {
			stock = stockRepos.getById(stockId);
			redisTemplate.opsForValue().set(key, stock, 600, TimeUnit.SECONDS);
		}

		if (stock.getAmount() <= 0) {
			throw new RuntimeException("库存不足");
		}
		return stock;
	}

	private int saleStockOptimistic(Stock stock) {
		stock.setAmount(stock.getAmount() - 1);
		int count = stockRepos.updateByIdOptimistic(stock);
		if (count == 0) {
			throw new RuntimeException("库存不足");
		}
		String key = RedisKeyPrefix.STOCK + stock.getId();
		redisTemplate.opsForValue().set(key, stock, 600, TimeUnit.SECONDS);
		return count;
	}

	private int saleStock(Stock stock) {
		stock.setAmount(stock.getAmount() - 1);
		int count = stockRepos.updateById(stock);

		String key = RedisKeyPrefix.STOCK + stock.getId();
		redisTemplate.opsForValue().set(key, stock, 600, TimeUnit.SECONDS);
		return count;
	}

	private Long createOrder(Stock stock) {
		StockOrder order = new StockOrder();
		order.setStockId(stock.getId());
		order.setAmount(1);
		order.setCreateTime(new Date());
		return orderRepos.insert(order);
	}

	@Override
	public Long createOrderRedisson(Long stockId) throws Exception {
		RLock lock = redissonClient.getLock("seckill");
		try {
			boolean isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS);
			if (isLocked) {
				// 检查库存
				Stock stock = checkStock(stockId);

				// 扣减库存
				saleStock(stock);

				// 生成订单
				Long orderId = createOrder(stock);
				return orderId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;
	}
}
