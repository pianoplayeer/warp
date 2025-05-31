package com.jxy.warp.match.engine;

import com.jxy.warp.common.consts.OrderDirection;
import com.jxy.warp.common.entity.Order;
import com.jxy.warp.common.infra.mapper.MatchDetailMapper;
import com.jxy.warp.common.infra.mapper.OrderMapper;
import com.jxy.warp.match.entity.OrderBook;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.annotation.Retention;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @date 2025/5/15
 * @package com.jxy.warp.match
 */
@Slf4j
@Service
public class MatchEngine {
	
	private static final Comparator<Order> BUY_COMPARATOR = (o1, o2) -> {
		if (o1.getPrice().compareTo(o2.getPrice()) != 0) {
			return o2.getPrice().subtract(o1.getPrice()).signum();
		}
		return (int) (o1.getSequenceId() - o2.getSequenceId());
	};
	
	private static final Comparator<Order> SELL_COMPARATOR = (o1, o2) -> {
		if (o1.getPrice().compareTo(o2.getPrice()) != 0) {
			return o1.getPrice().subtract(o2.getPrice()).signum();
		}
		return (int) (o1.getSequenceId() - o2.getSequenceId());
	};

	private OrderBook buyBook;
	
	private OrderBook sellBook;
	
	private BigDecimal marketPrice;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private MatchDetailMapper matchDetailMapper;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@PostConstruct
	private void init() {
		buyBook = new OrderBook(BUY_COMPARATOR);
		sellBook = new OrderBook(SELL_COMPARATOR);
		
		List<Order> activeOrders = orderMapper.getActiveOrders();
		activeOrders.forEach(order -> {
			switch (order.getDirection()) {
				case OrderDirection.BUY -> buyBook.addOrder(order);
				case OrderDirection.SELL -> sellBook.addOrder(order);
				
				default -> log.error("invalid order direction: {}", order.getDirection());
			}
		});
		
		processUnhandledOrders();
	}
	
	// 将买卖盘中尚未被处理的进行撮合
	private void processUnhandledOrders() {
		Order seller = sellBook.getFirstOrder(), buyer = buyBook.getFirstOrder();
		
		while (seller != null && buyer != null && buyer.getPrice().compareTo(seller.getPrice()) >= 0) {
			Order taker = (seller.getSequenceId() > buyer.getSequenceId()) ? seller : buyer;
			Order maker = (seller.getSequenceId() < buyer.getSequenceId()) ? seller : buyer;
			
			if (!processCounterPair(taker, maker)) {
				log.error("process counter pair fail");
			}
			
			seller = sellBook.getFirstOrder();
			buyer = buyBook.getFirstOrder();
		}
	}
	
	private boolean processCounterPair(Order taker, Order maker) {
		return Boolean.TRUE.equals(transactionTemplate.execute(status -> {
			BigDecimal dealQuantity = taker.getUnfilledQuantity().min(maker.getUnfilledQuantity());
			BigDecimal originMarketPrice = marketPrice;
			BigDecimal takerOriginUnfilled = new BigDecimal(taker.getUnfilledQuantity().toString());
			BigDecimal makerOriginUnfilled = new BigDecimal(maker.getUnfilledQuantity().toString());
			
			try {
				marketPrice = maker.getPrice();
				taker.setUnfilledQuantity(takerOriginUnfilled.subtract(dealQuantity));
				maker.setUnfilledQuantity(makerOriginUnfilled.subtract(dealQuantity));
				sellBook.addOrder(OrderDirection.SELL.equals(taker.getDirection()) ? taker : maker);
				buyBook.addOrder(OrderDirection.BUY.equals(taker.getDirection()) ? taker : maker);
				
				if (saveOrder(taker) && saveOrder(maker)) {
					return true;
				} else {
					throw new RuntimeException("save taker or maker order fail");
				}
			} catch (Exception e) {
				status.setRollbackOnly();
				
				marketPrice = originMarketPrice;
				taker.setUnfilledQuantity(takerOriginUnfilled);
				maker.setUnfilledQuantity(makerOriginUnfilled);
				
				sellBook.addOrder(OrderDirection.SELL.equals(taker.getDirection()) ? taker : maker);
				buyBook.addOrder(OrderDirection.BUY.equals(taker.getDirection()) ? taker : maker);
				
				log.error("process counter order pair fail", e);
				return false;
			}
		}));
	}
	
	private boolean saveOrder(Order order) {
		OrderBook book = getBook(order.getDirection());
		
		if (book == null) {
			return false;
		}
		
		return Boolean.TRUE.equals(transactionTemplate.execute(status -> {
			Order origin = null;
			try {
				book.addOrder(order);
				origin = orderMapper.getOrderById(order.getId());
				if (origin != null) {
					orderMapper.updateOrder(order);
				} else {
					orderMapper.insertOrder(order);
				}
				return true;
			} catch (Exception e) {
				if (origin != null) {
					book.addOrder(origin);
				} else {
					book.removeOrder(order);
				}
				status.setRollbackOnly();
				log.error("save order fail", e);
				
				return false;
			}
		}));
	}
	
	private OrderBook getBook(String direction) {
		return switch (direction) {
			case OrderDirection.BUY -> buyBook;
			case OrderDirection.SELL -> sellBook;
			
			default -> null;
		};
	}
}
