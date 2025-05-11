package com.jxy.warp.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @date 2025/5/3
 * @package com.jxy.warp.common.config
 */
@Configuration
@RefreshScope
public class NacosPropertyConfig {
	
	@Value("${warp.trade.user.systemId}")
	private String systemId;
	
	@Value("${warp.trade.topic.order}")
	private String orderTopic;
	
	public Long getSystemId() {
		return Long.parseLong(systemId);
	}
	
	public String getOrderTopic() {
		return orderTopic;
	}
	
}
