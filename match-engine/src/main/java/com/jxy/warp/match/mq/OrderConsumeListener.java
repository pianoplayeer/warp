package com.jxy.warp.match.mq;

import com.alibaba.fastjson.JSON;
import com.jxy.warp.common.consts.MQConst;
import com.jxy.warp.common.entity.Order;
import com.jxy.warp.match.engine.MatchEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @date 2025/5/15
 * @package com.jxy.warp.match
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "order-consume-group", topic = MQConst.ORDER_TOPIC)
public class OrderConsumeListener implements RocketMQListener<String> {
	
	@Autowired
	private MatchEngine matchEngine;
	
	@Override
	public void onMessage(String s) {
		Order order = (Order) JSON.parse(s);
		
	}
}
