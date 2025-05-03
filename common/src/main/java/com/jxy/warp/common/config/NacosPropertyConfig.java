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
	
	@Value("${warp.trade.systemId}")
	private String systemId;
	
	public Long getSystemId() {
		return Long.parseLong(systemId);
	}
	
}
