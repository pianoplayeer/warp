package com.jxy.warp.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @date 2025/5/3
 * @package com.jxy.warp.common.config
 */
@Configuration
@RefreshScope
public class NacosPropertyConfig {
	
	@Value("warp.trade.systemId")
	private Long systemId;
	
}
