package com.jxy.warp.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @date 2025/5/18
 * @package com.jxy.warp.common.entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchDetail {
	
	private Long id;
	
	private String takerOrderId;
	
	private String makerOrderId;
	
	private Long takerUserId;
	
	private Long makerUserId;
	
	private String direction;
	
	private LocalDateTime createdAt;
	
	private BigDecimal price;
	
	private BigDecimal quantity;
	
}
