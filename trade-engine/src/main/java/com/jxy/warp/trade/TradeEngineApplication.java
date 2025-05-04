package com.jxy.warp.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.jxy.warp"})
@MapperScan("com.jxy.warp.trade.mapper")
@EnableDiscoveryClient
public class TradeEngineApplication {

    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(TradeEngineApplication.class, args);
    }
}
