package com.jxy.warp.trade.mapper;

import java.math.BigDecimal;

import com.jxy.warp.trade.entity.TransferLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

@Mapper
public interface TransferLogMapper {

    @Insert("INSERT INTO transfer_logs(fromUserId, toUserId, amount, assetKind, status, type) VALUE (#{fromUserId}, #{toUserId}, #{amount}, #{assetKind}, #{status}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "transferId")
    void insertLog(TransferLog transferLog);

}
