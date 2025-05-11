package com.jxy.warp.trade.service;

import com.alibaba.fastjson.JSON;
import com.jxy.warp.trade.consts.AssetKind;
import com.jxy.warp.trade.consts.OrderDirection;
import com.jxy.warp.trade.consts.OrderStatus;
import com.jxy.warp.trade.entity.Order;
import com.jxy.warp.trade.infra.mapper.OrderMapper;
import com.jxy.warp.trade.infra.mq.OrderProducer;
import com.jxy.warp.trade.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
	
	@Autowired
	private OrderProducer orderProducer;
	
	public Order createOrder(Long sequenceId, Long userId, String direction,
							BigDecimal price, BigDecimal quantity) {
		
		if (!validateParams(sequenceId, userId, direction, price, quantity)) {
			return null;
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
		
		orderProducer.produceOrder(order);
		return order;
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

	private boolean validateParams(Long sequenceId, Long userId, String direction,
								   BigDecimal price, BigDecimal quantity) {
		if (!OrderDirection.BUY.equals(direction) && !OrderDirection.SELL.equals(direction)) {
			return false;
		} else if (price.signum() < 1 || quantity.signum() < 1) {
			return false;
		}
		
		return sequenceId <= orderUtil.getCurrentSequence();
	}
	
}
