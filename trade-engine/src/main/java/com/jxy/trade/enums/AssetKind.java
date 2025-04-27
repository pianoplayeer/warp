package com.jxy.trade.enums;

import lombok.Getter;

@Getter
public enum AssetKind {

    USD("USD"),

    BTC("BTC");

    private String kind;

    AssetKind(String kind) {
        this.kind = kind;
    }

}
