package org.millions.idea.ocr.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableDiscoveryClient
@EnableAsync
@ComponentScan("org.millions.idea")
@EnableFeignClients("org.millions.idea")
@EnableZuulProxy
public class MiOcrGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiOcrGatewayApplication.class, args);
	}
}
