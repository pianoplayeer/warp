package com.jxy.warp.trade.service;

import java.math.BigDecimal;

import com.jxy.warp.trade.consts.AssetStatus;
import com.jxy.warp.trade.consts.TransferStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jxy.warp.trade.enums.TransferType;
import com.jxy.warp.trade.mapper.AssetMapper;
import com.jxy.warp.trade.mapper.TransferLogMapper;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TransferService {

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private TransferLogMapper transferLogMapper;

    @Transactional(rollbackFor = Throwable.class)
    public boolean tryTransfer(TransferType transferType, Long from, Long to,
                               String kind, BigDecimal amount, boolean checkBalance) {
    
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status != STATUS_COMMITTED) {
                    transferLogMapper.insertLog(from, to, amount, kind,
                            transferType.name(), TransferStatus.FAIL);
                }
            }
        });
        
        BigDecimal fromBalance = assetMapper.getAssetAmountForUpdate(from, transferType.getFrom());
        BigDecimal toBalance = assetMapper.getAssetAmountForUpdate(to, transferType.getTo());
        
        if (checkBalance && fromBalance.compareTo(toBalance) < 0) {
            return false;
        }
        
        transfer(from, kind, fromBalance.subtract(amount), transferType.getFrom());
        transfer(to, kind, toBalance.add(amount), transferType.getTo());
        
        transferLogMapper.insertLog(from, to, amount, kind, transferType.name(), TransferStatus.SUCCESS);
        return true;
    }
    
    private void transfer(Long userId, String kind, BigDecimal amount, String assetStatus) {
        if (assetStatus.equals(AssetStatus.FROZEN)) {
            assetMapper.updateAssetFrozenByAmount(userId, kind, amount);
        } else {
            assetMapper.updateAssetAvailableByAmount(userId, kind, amount);
        }
    }
}
