package com.xu.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.xu.entity.StockOrder;
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

	@Override
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
		Stock stock = stockRepos.getById(stockId);
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
		return count;
	}

	private int saleStock(Stock stock) {
		stock.setAmount(stock.getAmount() - 1);
		return stockRepos.updateById(stock);
	}

	private Long createOrder(Stock stock) {
		StockOrder order = new StockOrder();
		order.setStockId(stock.getId());
		order.setAmount(1);
		order.setCreateTime(new Date());
		return orderRepos.insert(order);
	}
}
