package com.jxy.trade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jxy.trade.mapper")
public class TradeEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeEngineApplication.class, args);
    }
}
