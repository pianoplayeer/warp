package com.jxy.trade.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxy.trade.entity.TransferLog;
import com.jxy.trade.enums.AssetKind;
import com.jxy.trade.enums.TransferType;
import com.jxy.trade.mapper.AssetMapper;
import com.jxy.trade.mapper.TransferLogMapper;

@Service
public class TransferService {

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private TransferLogMapper transferLogMapper;

    @Transactional
    public boolean tryTransfer(TransferType transferType, Long from, Long to,
                               AssetKind kind, BigDecimal amount, boolean checkBalance) {



    }

}
