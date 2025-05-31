package com.jxy.warp.trade;

import com.jxy.warp.common.consts.OrderDirection;
import com.jxy.warp.trade.service.OrderService;
import com.jxy.warp.trade.util.OrderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * @date 2025/5/5
 * @package com.jxy.warp.trade
 */
@SpringBootTest
public class OrderTest {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderUtil orderUtil;
	
	@Test
	public void testOrder() {
		orderService.createOrder(orderUtil.getNextSequenceId(), 3L, OrderDirection.SELL, new BigDecimal("0.001"), new BigDecimal(2));
		orderService.createOrder(orderUtil.getNextSequenceId(), 4L, OrderDirection.BUY, new BigDecimal("0.002"), new BigDecimal(1));
		System.out.println(orderService.getActiveOrders());
		System.out.println(orderService.getOrdersByUserId(3L));
		System.out.println(orderService.getOrdersByUserId(4L));
	}
}
