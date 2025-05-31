package com.jxy.warp.common.infra.mapper;

import com.jxy.warp.common.entity.TransferLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TransferLogMapper {

    @Insert("INSERT INTO transfer_logs(fromUserId, toUserId, amount, assetKind, status, type) VALUE (#{fromUserId}, #{toUserId}, #{amount}, #{assetKind}, #{status}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "transferId")
    void insertLog(TransferLog transferLog);

}
