package com.jxy.trade.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Asset {

    private Long userId;

    private String kind;

    private BigDecimal available;

    private BigDecimal frozen;

}
