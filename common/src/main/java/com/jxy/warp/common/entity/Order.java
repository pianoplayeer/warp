package com.jxy.warp.common.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
