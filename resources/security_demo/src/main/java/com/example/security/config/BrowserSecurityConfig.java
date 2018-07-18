/***
 * @pName security
 * @name BrowserSecurityConfig
 * @user HongWei
 * @date 2018/7/18
 * @desc
 */
package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 应用安全适配器配置
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 验证表单
        http.formLogin()
                .and()
                .authorizeRequests()    //下面都是需要授权的配置
                .anyRequest()   //任何请求
                .authenticated();   //都需要身份认证
    }
}
