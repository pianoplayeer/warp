package com.jxy.warp.match.mq;

import com.alibaba.fastjson.JSON;
import com.jxy.warp.common.consts.MQConst;
import com.jxy.warp.common.entity.MatchDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @date 2025/5/18
 * @package com.jxy.warp.match.mq
 */
@Slf4j
@Component
public class MatchDetailProducer {
	
	@Autowired
	private RocketMQTemplate rocketMQTemplate;
	
	public void sendMatchDetail(MatchDetail detail) {
		Message<String> message = MessageBuilder.withPayload(JSON.toJSONString(detail))
										  .setHeader(RocketMQHeaders.TAGS, detail.getDirection())
										  .setHeader(RocketMQHeaders.KEYS, detail.getId())
										  .build();
		rocketMQTemplate.asyncSend(MQConst.MATCH_TOPIC, message, new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				log.info("send match detail success: {}", detail);
			}
			
			@Override
			public void onException(Throwable e) {
				log.error("fail to send match detail {}", detail, e);
			}
		});
	}
	
}
