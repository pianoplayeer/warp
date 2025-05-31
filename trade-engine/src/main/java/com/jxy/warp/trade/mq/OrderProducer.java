package com.jxy.warp.trade.mq;

import com.alibaba.fastjson.JSON;
import com.jxy.warp.common.config.NacosPropertyConfig;
import com.jxy.warp.common.consts.MQConst;
import com.jxy.warp.common.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @date 2025/5/11
 * @package com.jxy.warp.trade.infra.mq
 */
@Slf4j
@Component
public class OrderProducer {
	
	@Autowired
	private RocketMQTemplate mqTemplate;
	
	public void produceOrder(Order order) {
		Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(order))
										  .setHeader(RocketMQHeaders.KEYS, order.getId())
										  .setHeader(RocketMQHeaders.TAGS, order.getDirection())
										  .setHeader("orderId", order.getId())
										  .build();
		
		var result = mqTemplate.sendMessageInTransaction(MQConst.ORDER_TOPIC, message, order);
		log.info("send order {} to mq, topic: {}", message, MQConst.ORDER_TOPIC);
	}
}
