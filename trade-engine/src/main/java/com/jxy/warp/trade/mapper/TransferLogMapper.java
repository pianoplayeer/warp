package com.jxy.warp.trade.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransferLogMapper {

    @Insert("INSERT INTO transfer_logs(fromUserId, toUserId, amount, assetKind, status, type) VALUE (#{fromId}, #{toId}, #{amount}, #{kind}, #{status}, #{type})")
    void insertLog(Long fromId, Long toId, BigDecimal amount, String kind, String type, String status);

}
