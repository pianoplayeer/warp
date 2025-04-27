package com.jxy.trade.enums;

public enum TransferStatus {

    // 暂时只考虑这两种情况
    SUCCESS("success"),

    FAIL("fail");

    private String msg;

    TransferStatus(String msg) {
        this.msg = msg;
    }
}
