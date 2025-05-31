package com.jxy.warp.match.entity;

import com.jxy.warp.common.entity.Order;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * @date 2025/5/16
 * @package com.jxy.warp.match.entity
 */
public class OrderBook {
	
	private TreeSet<Order> book;
	
	public OrderBook(Comparator<Order> comparator) {
		book = new TreeSet<>(comparator);
	}
	
	public void addOrder(Order order) {
		book.add(order);
	}
	
	public void addOrders(List<Order> orders) {
		book.addAll(orders);
	}
	
	public void removeOrder(Order order) {
		book.remove(order);
	}
	
	public Order getFirstOrder() {
		return book.isEmpty() ? null : book.first();
	}
}
