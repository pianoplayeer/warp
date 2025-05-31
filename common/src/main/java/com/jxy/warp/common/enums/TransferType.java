package com.jxy.warp.common.enums;

import com.jxy.warp.common.consts.AssetStatus;
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
