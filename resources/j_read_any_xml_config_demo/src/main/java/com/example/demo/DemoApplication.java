package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        Properties properties = new Properties();
        InputStream resourceAsStream = DemoApplication.class.getResourceAsStream("/application.properties");
        properties.load(resourceAsStream);
        ConfigLoader.configure(properties.getProperty("app.config.url"));
        System.out.println(GlobalConfig.getRabbitMQ());
        System.out.println(GlobalConfig.getRedis());
        SpringApplication.run(DemoApplication.class, args);
    }
}
