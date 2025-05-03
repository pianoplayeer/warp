package com.jxy.warp.trade.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface AssetMapper {
    
    @Select("SELECT available FROM assets where userId = #{userId} AND kind = #{kind} FOR UPDATE")
    BigDecimal getAssetAvailableAmountForUpdate(Long userId, String kind);
    
    @Select("SELECT frozen FROM assets where userId = #{userId} AND kind = #{kind} FOR UPDATE")
    BigDecimal getAssetFrozenAmountForUpdate(Long userId, String kind);

    @Update("UPDATE assets SET available = #{amount} WHERE userId = #{userId} AND kind = #{kind}")
    void updateAssetAvailableByAmount(Long userId, String kind, BigDecimal amount);

    @Update("UPDATE assets SET frozen = #{amount} WHERE userId = #{userId} AND kind = #{kind}")
    void updateAssetFrozenByAmount(Long userId, String kind, BigDecimal amount);
    
}
