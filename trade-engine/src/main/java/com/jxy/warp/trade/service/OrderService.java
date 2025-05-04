package com.jxy.warp.trade.service;

import com.jxy.warp.trade.consts.AssetKind;
import com.jxy.warp.trade.consts.OrderDirection;
import com.jxy.warp.trade.consts.OrderStatus;
import com.jxy.warp.trade.entity.Order;
import com.jxy.warp.trade.mapper.OrderMapper;
import com.jxy.warp.trade.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @date 2025/5/4
 * @package com.jxy.warp.trade.service
 */
@Slf4j
@Service
public class OrderService {
	
	@Autowired
	private OrderUtil orderUtil;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private AssetService assetService;
	
	@Transactional(rollbackFor = Throwable.class)
	public void createOrder(Long sequenceId, Long userId, String direction,
							BigDecimal price, BigDecimal quantity) {
		
		switch (direction) {
			case OrderDirection.BUY -> {
				if (!assetService.freeze(userId, AssetKind.USD, price.multiply(quantity))) {
					return;
				}
			}
			
			case OrderDirection.SELL -> {
				if (!assetService.freeze(userId, AssetKind.BTC, price.multiply(quantity))) {
					return;
				}
			}
			
			default -> {
				log.error("invalid order direction: {}", direction);
				return;
			}
		}
		
		Order order = new Order();
		LocalDateTime now = LocalDateTime.now();
		
		order.setId(orderUtil.generateOrderIdPrefix() + sequenceId);
		order.setCreatedAt(now);
		order.setUpdatedAt(now);
		order.setStatus(OrderStatus.PENDING);
		order.setDirection(direction);
		order.setSequenceId(sequenceId);
		order.setUserId(userId);
		order.setPrice(price);
		order.setQuantity(quantity);
		order.setUnfilledQuantity(quantity);
		
		orderMapper.insertOrder(order);
	}
	
	public List<Order> getOrdersByUserId(Long userId) {
		return orderMapper.getOrdersByUserId(userId);
	}
	
	public List<Order> getActiveOrdersByUserId(Long userId) {
		return orderMapper.getActiveOrdersByUserId(userId);
	}
	
	public List<Order> getActiveOrders() {
		return orderMapper.getActiveOrders();
	}
	
	public Order getOrderById(String orderId) {
		return orderMapper.getOrderById(orderId);
	}

}
