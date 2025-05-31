package com.jxy.warp.trade.mq;


import com.jxy.warp.common.consts.AssetKind;
import com.jxy.warp.common.consts.OrderDirection;
import com.jxy.warp.common.entity.Order;
import com.jxy.warp.trade.exception.AssetFreezeException;
import com.jxy.warp.common.infra.mapper.OrderMapper;
import com.jxy.warp.trade.service.AssetService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date 2025/5/11
 * @package com.jxy.warp.trade.conf
 */
@RocketMQTransactionListener
@Slf4j
public class OrderTransactionListener implements RocketMQLocalTransactionListener {
	
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
		Order order = (Order) o;
		String assetKind;
		
		switch (order.getDirection()) {
			case OrderDirection.BUY -> assetKind = AssetKind.USD;
			
			case OrderDirection.SELL -> assetKind = AssetKind.BTC;
			
			default -> {
				log.error("invalid order direction: {}", order.getDirection());
				return RocketMQLocalTransactionState.ROLLBACK;
			}
		}
		
		if (!assetService.freeze(order.getUserId(), assetKind, order.getPrice().multiply(order.getQuantity()))) {
			throw new AssetFreezeException("fail to freeze");
		}
		
		orderMapper.insertOrder(order);
		return RocketMQLocalTransactionState.COMMIT;
	}
	
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		String orderId = (String) message.getHeaders().get("orderId");
		Order order = orderMapper.getOrderById(orderId);
		
		if (order != null) {
			log.info("mq check back success");
			return RocketMQLocalTransactionState.COMMIT;
		} else {
			log.error("mq check back fail");
			return RocketMQLocalTransactionState.ROLLBACK;
		}
	}
}
