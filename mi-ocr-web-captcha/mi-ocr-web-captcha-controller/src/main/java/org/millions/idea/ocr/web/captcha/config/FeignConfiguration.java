/***
 * @pName mi-ocr-web-captcha
 * @name FeignConfiguration
 * @user HongWei
 * @date 2018/6/24
 * @desc
 */
package org.millions.idea.ocr.web.captcha.config;

import feign.Logger;
import feign.codec.Encoder;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Decoder decoder(){
        return new JacksonDecoder();
    }

    @Bean
    public Encoder encoder(){
        return new JacksonEncoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        /* NONE, 不记录日志 (默认)。
        BASIC, 只记录请求方法和URL以及响应状态代码和执行时间。
        HEADERS, 记录请求和应答的头的基本信息。
        FULL, 记录请求和响应的头信息，正文和元数据。*/
        return Logger.Level.FULL;
    }
}
