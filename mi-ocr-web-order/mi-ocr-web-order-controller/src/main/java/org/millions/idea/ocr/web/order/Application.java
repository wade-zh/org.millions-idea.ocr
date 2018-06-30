package org.millions.idea.ocr.web.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableDiscoveryClient
@ComponentScan(value = {"org.millions.idea.*"})
@MapperScan(value = {"org.millions.idea.ocr.web.finance.repository.mapper"})
@EntityScan(value = {"org.millions.idea.ocr.web.finance.entity.db"})
public class Application {
	final static Logger logger = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.debug("Startup success");
	}
}
