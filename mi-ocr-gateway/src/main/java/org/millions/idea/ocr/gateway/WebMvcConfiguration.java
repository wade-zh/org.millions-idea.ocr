/***
 * @pName mi-ocr-gateway
 * @name WebMvcConfiguration
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package org.millions.idea.ocr.gateway;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer WebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //        registry.addInterceptor(new ExceptionInterceptor()).addPathPatterns("/**").excludePathPatterns("/user");
                registry.addInterceptor(new ExceptionInterceptor()).addPathPatterns("/**");
            }
        };
    }
}
