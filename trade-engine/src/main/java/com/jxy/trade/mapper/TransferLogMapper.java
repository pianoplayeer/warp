package com.jxy.trade.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jxy.trade.consts.AssetKind;
import com.jxy.trade.consts.TransferStatus;
import com.jxy.trade.enums.TransferType;

@Mapper
public interface TransferLogMapper {

    @Insert("INSERT INTO transfer_logs(fromUserId, toUserId, amount, assetKind, status, type) VALUE (#{fromId}, #{toId}, #{amount}, #{kind}, #{status}, #{type})")
    void insertLog(Long fromId, Long toId, BigDecimal amount, String kind, String type, String status);

}
