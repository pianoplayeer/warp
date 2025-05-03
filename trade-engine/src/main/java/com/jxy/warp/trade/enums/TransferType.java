package com.jxy.warp.trade.enums;

import com.jxy.warp.trade.consts.AssetStatus;
import lombok.Getter;

@Getter
public enum TransferType {

    AVAILABLE_TO_AVAILABLE(AssetStatus.AVAILABLE, AssetStatus.AVAILABLE),

    AVAILABLE_TO_FROZEN(AssetStatus.AVAILABLE, AssetStatus.FROZEN),

    FROZEN_TO_AVAILABLE(AssetStatus.FROZEN, AssetStatus.AVAILABLE);

    private final String from;

    private final String to;

    TransferType(String from, String to) {
        this.from = from;
        this.to = to;
    }
}
