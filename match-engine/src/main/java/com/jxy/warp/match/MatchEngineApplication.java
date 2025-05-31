package com.jxy.warp.match;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jxy.warp"})
@MapperScan("com.jxy.warp.common.infra.mapper")
public class MatchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchEngineApplication.class, args);
	}
	
}
