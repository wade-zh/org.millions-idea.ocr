package org.millions.idea.ocr.web.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;



@EnableFeignClients("org.millions.idea.ocr.web.captcha.agent")
@SpringBootApplication(scanBasePackages = "org.millions.idea.ocr.web.captcha.agent")
@ComponentScan("org.millions.idea.ocr.web.captcha.agent")
@EnableDiscoveryClient
@EnableAsync
public class MiOcrControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiOcrControllerApplication.class, args);
	}


	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
