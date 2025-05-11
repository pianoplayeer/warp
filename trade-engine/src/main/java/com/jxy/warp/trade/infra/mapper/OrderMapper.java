package com.jxy.warp.trade.infra.mapper;

import com.jxy.warp.trade.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @date 2025/5/4
 * @package com.jxy.warp.trade.mapper
 */
@Mapper
public interface OrderMapper {
	
	@Select("SELECT * FROM orders WHERE userId = #{userId}")
	List<Order> getOrdersByUserId(Long userId);
	
	@Select("SELECT * FROM orders WHERE userId = #{userId} AND status IN ('partial completed', 'pending')")
	List<Order> getActiveOrdersByUserId(Long userId);
	
	@Select("SELECT * FROM orders WHERE status IN ('partial completed', 'pending')")
	List<Order> getActiveOrders();
	
	@Select("SELECT * FROM orders WHERE id = #{orderId}")
	Order getOrderById(String orderId);
	
	@Select("SELECT * FROM orders WHERE direction = #{direction} " +
					"AND status IN ('partial completed', 'pending') " +
					"ORDER BY price DESC, sequenceId ASC FOR UPDATE")
	List<Order> getActiveOrdersByDirectionForUpdate(String direction);
	
	@Insert("INSERT INTO orders(id, direction, price, quantity, sequenceId, status, unfilledQuantity, userId) " +
					"VALUES (#{id}, #{direction}, #{price}, #{quantity}, #{sequenceId}, #{status}, #{unfilledQuantity}, #{userId})")
	void insertOrder(Order order);
	
}
