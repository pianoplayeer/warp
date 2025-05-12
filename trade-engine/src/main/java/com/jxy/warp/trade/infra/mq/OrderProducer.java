package com.jxy.warp.trade.infra.mq;

import com.alibaba.fastjson.JSON;
import com.jxy.warp.common.config.NacosPropertyConfig;
import com.jxy.warp.trade.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @date 2025/5/11
 * @package com.jxy.warp.trade.infra.mq
 */
@Slf4j
@Service
public class OrderProducer {
	
	@Autowired
	private NacosPropertyConfig nacosPropertyConfig;
	
	@Autowired
	private RocketMQTemplate mqTemplate;
	
	public void produceOrder(Order order) {
		Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(order))
										  .setHeader("orderId", order.getId())
										  .build();
		
		mqTemplate.sendMessageInTransaction(nacosPropertyConfig.getOrderTopic(), message, order);
		log.info("send order {} to mq", message);
	}
}
