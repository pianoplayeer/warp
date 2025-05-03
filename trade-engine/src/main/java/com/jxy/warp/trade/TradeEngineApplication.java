package com.jxy.warp.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.jxy.trade.mapper")
@EnableDiscoveryClient
public class TradeEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeEngineApplication.class, args);
    }
}
