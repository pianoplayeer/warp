package com.jxy.warp.trade.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @date 2025/5/4
 * @package com.jxy.warp.trade.util
 */
@Component
public class OrderUtil {
	
	private static final String SEQ_ID_KEY = "sequenceId";
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public String generateOrderIdPrefix() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-"));
	}
	
	public Long getNextSequenceId() {
		return redisTemplate.opsForValue().increment(SEQ_ID_KEY);
	}
	
}
