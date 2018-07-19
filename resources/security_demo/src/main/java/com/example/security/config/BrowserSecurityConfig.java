/***
 * @pName security
 * @name BrowserSecurityConfig
 * @user HongWei
 * @date 2018/7/18
 * @desc
 */
package com.example.security.config;

import com.example.security.core.BrowserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 应用安全适配器配置
 */
@Component
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BrowserProperties properties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 验证表单
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form") //使用自定义的登录api
                .and()
                .authorizeRequests()    //下面都是需要授权的配置
                .antMatchers("/authentication/require"
                        ,properties.getBrowser())
                .permitAll()    // 再此之后的所有请求将需要验证权限
                .anyRequest()   //任何请求
                .authenticated()
                .and()
                .csrf().disable();   //都需要身份认证
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
