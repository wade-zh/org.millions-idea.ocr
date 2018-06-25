package org.millions.idea.ocr.web.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@ComponentScan(basePackages = "org.millions.idea")
@EnableFeignClients
@EnableDiscoveryClient
@EnableAsync
public class MiOcrControllerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MiOcrControllerApplication.class, args);
	}
}
