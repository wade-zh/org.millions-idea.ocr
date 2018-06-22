package org.millions.idea.ocr.web.user.controller;

import com.cuisongliu.druid.autoconfigure.DruidAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DruidAutoConfiguration.class })
@ComponentScan(basePackages = "org.millions.idea")
@EnableDiscoveryClient
public class MiOcrWebUserEntranceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiOcrWebUserEntranceApplication.class, args);
	}
}
