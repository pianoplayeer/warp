package com.jxy.warp.trade;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.jxy.warp"})
@MapperScan("com.jxy.warp.trade.infra.mapper")
@EnableDiscoveryClient
@Import(RocketMQAutoConfiguration.class)
public class TradeEngineApplication {

    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(TradeEngineApplication.class, args);
    }
}
