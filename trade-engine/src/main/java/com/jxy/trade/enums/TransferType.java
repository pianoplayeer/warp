package com.jxy.trade.enums;

import com.jxy.trade.consts.TransferConst;

import lombok.Getter;

@Getter
public enum TransferType {

    AVAILABLE_TO_AVAILABLE(TransferConst.AVAILABLE, TransferConst.AVAILABLE),

    AVAILABLE_TO_FROZEN(TransferConst.AVAILABLE, TransferConst.FROZEN),

    FROZEN_TO_AVAILABLE(TransferConst.FROZEN, TransferConst.AVAILABLE);

    private final String from;

    private final String to;

    TransferType(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
