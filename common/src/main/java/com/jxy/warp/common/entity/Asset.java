package com.jxy.warp.common.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Asset {

    private Long userId;

    private String kind;

    private BigDecimal available;

    private BigDecimal frozen;

}
