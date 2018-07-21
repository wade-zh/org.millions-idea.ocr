package org.millions.idea.ocr.app.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.millions.idea.ocr.app")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
