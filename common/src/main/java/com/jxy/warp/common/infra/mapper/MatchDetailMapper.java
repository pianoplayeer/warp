package com.jxy.warp.common.infra.mapper;

import com.jxy.warp.common.entity.MatchDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @date 2025/5/18
 * @package com.jxy.warp.common.infra.mapper
 */
@Mapper
public interface MatchDetailMapper {
	
	@Insert("INSERT INTO match_details(takerOrderId, makerOrderId, takerUserId, makerUserId, direction, price, quantity, status) " +
					"VALUE (#{takerOrderId}, #{makerOrderId}, #{takerUserId}, #{makerUserId}, #{direction}, #{price}, #{quantity}, #{status})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertMatchDetail(MatchDetail detail);
	
	@Select("SELECT * FROM match_details")
	List<MatchDetail> getAllMatchDetails();
	
}
