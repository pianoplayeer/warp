package com.jxy.warp.trade.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @date 2025/5/4
 * @package com.jxy.warp.trade.entity
 */
@Data
public class Order {
	
	// createdAt + '-' + sequenceId
	private String id;
	
	private Long sequenceId;
	
	private Long userId;
	
	private String direction;
	
	private BigDecimal price;
	
	private BigDecimal quantity;
	
	private BigDecimal unfilledQuantity;
	
	private String status;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
