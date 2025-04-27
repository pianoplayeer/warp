package com.jxy.trade.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.jxy.trade.enums.AssetKind;
import com.jxy.trade.enums.TransferStatus;
import com.jxy.trade.enums.TransferType;

@Mapper
public interface TransferLogMapper {

    @Insert("INSERT INTO transfer_logs(fromUserId, toUserId, amount, assetKind, status, type) VALUE (#{fromId}, #{toId}, #{amount}, #{kind.kind}, #{status.msg}, #{type.name()})")
    void insertLog(Long fromId, Long toId, BigDecimal amount, AssetKind kind, TransferType type, TransferStatus status);

}
