package com.jxy.warp.match.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @date 2025/5/31
 * @package com.jxy.warp.match.mq
 */
@RocketMQTransactionListener
@Slf4j
public class MatchTransactionListener implements RocketMQLocalTransactionListener {
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
		return null;
	}
	
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		return null;
	}
}
