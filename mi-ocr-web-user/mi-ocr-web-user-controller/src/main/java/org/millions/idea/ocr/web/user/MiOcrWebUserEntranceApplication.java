package org.millions.idea.ocr.web.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(value = {"org.millions.idea.*"})
@MapperScan(value = {"org.millions.idea.ocr.web.user.repository.mapper"})
public class MiOcrWebUserEntranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiOcrWebUserEntranceApplication.class, args);
	}
}
