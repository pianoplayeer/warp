package com.jxy.warp.trade;

import com.jxy.warp.trade.consts.AssetKind;
import com.jxy.warp.trade.engine.AssetEngine;
import com.jxy.warp.trade.enums.TransferType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * @date 2025/5/4
 * @package com.jxy.warp.trade
 */
@SpringBootTest
public class AssetTest {
	
	@Autowired
	private AssetEngine assetEngine;
	
	@Test
	public void testAsset() {
		assetEngine.saveAsset(3L, AssetKind.USD, new BigDecimal(1));
		assetEngine.transfer(TransferType.AVAILABLE_TO_AVAILABLE, 3L, 4L, AssetKind.USD, new BigDecimal("0.5"));
	}
}
