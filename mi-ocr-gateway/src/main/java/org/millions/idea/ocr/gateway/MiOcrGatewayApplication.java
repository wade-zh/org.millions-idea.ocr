package org.millions.idea.ocr.gateway;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import org.millions.idea.ocr.gateway.filter.ZuulExpcetionFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableDiscoveryClient
@EnableAsync
@ComponentScan(basePackages = "org.millions.idea")
@EnableFeignClients("org.millions.idea")
@EnableZuulProxy
@EnableCircuitBreaker //open breaker
public class MiOcrGatewayApplication {

	@Bean
	public ZuulExpcetionFilter zuulExpcetionFilter(){
		return new ZuulExpcetionFilter();
	}


	public static void main(String[] args) throws Exception {
		SpringApplication.run(MiOcrGatewayApplication.class, args);
	}
}
