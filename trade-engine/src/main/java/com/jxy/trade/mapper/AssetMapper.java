package com.jxy.trade.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import com.jxy.trade.enums.AssetKind;

@Mapper
public interface AssetMapper {

    @Update("UPDATE assets SET available = available + #{amount} WHERE userId = #{userId} AND kind = #{kind}")
    void updateAssetAvailableByAmount(Long userId, String kind, BigDecimal amount);

    @Update("UPDATE assets SET frozen = frozen + #{amount} WHERE userId = #{userId} AND kind = #{kind}")
    void updateAssetFrozenByAmount(Long userId, String kind, BigDecimal amount);

}
