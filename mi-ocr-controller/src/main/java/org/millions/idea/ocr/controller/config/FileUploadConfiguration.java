/***
 * @pName org.millions-idea.ocr
 * @name FileUploadConfiguration
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.controller.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("100KB");
        factory.setMaxRequestSize("1MB");
        return factory.createMultipartConfig();
    }
}