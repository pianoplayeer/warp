package com.jxy.warp.trade.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransferLog {

    private String transferId;

    private BigDecimal amount;

    private String assetKind;

    private LocalDateTime createdAt;

    private String status;

    // TransferType.name()
    private String type;

    private Long fromUserId;

    private Long toUserId;

}
