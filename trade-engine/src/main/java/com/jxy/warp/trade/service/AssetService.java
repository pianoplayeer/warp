package com.jxy.warp.trade.service;

import com.jxy.warp.common.config.NacosPropertyConfig;
import com.jxy.warp.trade.enums.TransferType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @date 2025/5/2
 * @package com.jxy.trade.engine
 */
@Slf4j
@Service
public class AssetService {
	
	@Autowired
	private TransferService transferService;
	
	@Autowired
	private NacosPropertyConfig nacosPropertyConfig;
	
	
	public boolean transfer(TransferType transferType, Long from, Long to,
						 String kind, BigDecimal amount) {
		try {
			boolean success = transferService.tryTransfer(transferType, from, to,
					kind, amount, true);
			
			if (success) {
				log.info("transfer success: {}${} -> {}${} {}", from, transferType.getFrom(), to, transferType.getTo(), amount);
			} else {
				log.warn("transfer fail, balance not enough");
			}
			
			return success;
		} catch (Exception e) {
			log.error("transfer fail: {}", e.getMessage());
			return false;
		}
	}
	
	public boolean freeze(Long userId, String kind, BigDecimal amount) {
		boolean success = transfer(TransferType.AVAILABLE_TO_FROZEN, userId, userId, kind, amount);
		if (success) {
			log.info("freeze user {} asset {} {}", userId, kind, amount);
		} else {
			log.error("fail to freeze user {} asset {} {}", userId, kind, amount);
		}
		
		return success;
	}
	
	public boolean unfreeze(Long userId, String kind, BigDecimal amount) {
		boolean success = transfer(TransferType.FROZEN_TO_AVAILABLE, userId, userId, kind, amount);
		if (success) {
			log.info("unfreeze user {} asset {} {}", userId, kind, amount);
		} else {
			log.error("fail to unfreeze user {} asset {} {}", userId, kind, amount);
		}
		
		return success;
	}
	
	public void saveAsset(Long userId, String kind, BigDecimal amount) {
		try {
			transferService.tryTransfer(TransferType.AVAILABLE_TO_AVAILABLE,
					nacosPropertyConfig.getSystemId(), userId, kind, amount, false);
			log.info("{} save asset {}: {}", userId, kind, amount);
		} catch (Exception e) {
			log.error("{} fail to save asset {} {}: {}", userId, amount, kind, e.getMessage());
		}
	}
}
