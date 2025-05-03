package com.jxy.warp.trade.service;

import java.math.BigDecimal;

import com.jxy.warp.common.config.NacosPropertyConfig;
import com.jxy.warp.trade.consts.AssetStatus;
import com.jxy.warp.trade.consts.TransferStatus;
import com.jxy.warp.trade.entity.TransferLog;
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
    
    @Autowired
    private NacosPropertyConfig nacosPropertyConfig;

    @Transactional(rollbackFor = Throwable.class)
    public void saveAsset(Long userId, String kind, BigDecimal amount) {
        Long systemId = nacosPropertyConfig.getSystemId();
        
        TransferLog transferLog = new TransferLog();
        transferLog.setFromUserId(systemId);
        transferLog.setToUserId(userId);
        transferLog.setAssetKind(kind);
        transferLog.setAmount(amount);
        transferLog.setType(TransferType.AVAILABLE_TO_AVAILABLE.name());
        transferLog.setStatus(TransferStatus.SUCCESS);
    
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status != STATUS_COMMITTED) {
                    transferLog.setStatus(TransferStatus.FAIL);
                    transferLogMapper.insertLog(transferLog);
                }
            }
        });
        
        BigDecimal systemAvailable = assetMapper.getAssetAvailableAmountForUpdate(systemId, kind);
        BigDecimal userAvailable = assetMapper.getAssetAvailableAmountForUpdate(userId, kind);
        
        assetMapper.updateAssetAvailableByAmount(systemId, kind, systemAvailable.subtract(amount));
        assetMapper.updateAssetAvailableByAmount(userId, kind, userAvailable.add(amount));
        
        transferLogMapper.insertLog(transferLog);
    }
    
    @Transactional(rollbackFor = Throwable.class)
    public boolean tryTransfer(TransferType transferType, Long from, Long to,
                               String kind, BigDecimal amount, boolean checkBalance) {
        TransferLog transferLog = new TransferLog();
        transferLog.setFromUserId(from);
        transferLog.setToUserId(to);
        transferLog.setAssetKind(kind);
        transferLog.setAmount(amount);
        transferLog.setType(TransferType.AVAILABLE_TO_AVAILABLE.name());
        transferLog.setStatus(TransferStatus.SUCCESS);
        
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status != STATUS_COMMITTED) {
                    transferLog.setStatus(TransferStatus.FAIL);
                    transferLogMapper.insertLog(transferLog);
                }
            }
        });
        
        BigDecimal fromBalance = getAssetAmount(from, transferType.getFrom(), kind);
        BigDecimal toBalance = getAssetAmount(to, transferType.getTo(), kind);
        
        if (checkBalance && fromBalance.compareTo(toBalance) < 0) {
            return false;
        }
        
        transfer(from, kind, fromBalance.subtract(amount), transferType.getFrom());
        transfer(to, kind, toBalance.add(amount), transferType.getTo());
        
        transferLogMapper.insertLog(transferLog);
        return true;
    }
    
    private void transfer(Long userId, String kind, BigDecimal amount, String assetStatus) {
        if (assetStatus.equals(AssetStatus.FROZEN)) {
            assetMapper.updateAssetFrozenByAmount(userId, kind, amount);
        } else {
            assetMapper.updateAssetAvailableByAmount(userId, kind, amount);
        }
    }
    
    private BigDecimal getAssetAmount(Long userId, String assetStatus, String kind) {
        if (AssetStatus.AVAILABLE.equals(assetStatus)) {
            return assetMapper.getAssetAvailableAmountForUpdate(userId, kind);
        } else {
            return assetMapper.getAssetFrozenAmountForUpdate(userId, kind);
        }
    }
}
