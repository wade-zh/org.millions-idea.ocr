/***
 * @pName mi-ocr-web-app
 * @name SpringSecurityConfiguration
 * @user HongWei
 * @date 2018/7/21
 * @desc security安全信息框架配置类
 */
package org.millions.idea.ocr.app.ui.config;


import org.millions.idea.ocr.app.entity.config.UserProperties;
import org.millions.idea.ocr.web.common.utility.encrypt.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserProperties userProperties;


    @Autowired
    @Qualifier("CustomerDetailsService")
    private UserDetailsService customerDetailsService;

    /**
     * 密码加密
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customerDetailsService);
        auth.userDetailsService(customerDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return Md5Util.getMd5((String) charSequence);
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.equals(Md5Util.getMd5(s));
            }
        });
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    /**
     * 保护机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(userProperties.getSecurity().getLoginPage()) // 登录入口
                .successForwardUrl("/")  // 验证成功跳转到指定路由或页面
                .failureForwardUrl("/")  // 验证失败跳转到指定路由或页面
                .loginProcessingUrl(userProperties.getSecurity().getProcessingUrl())    // 登录验证接口
                .and()
                .authorizeRequests()    // 定义哪些路由或页面需要启用保护机制
                .antMatchers(
                        userProperties.getSecurity().getLoginPage() // 登录页
                        , "/api/**"
                        , "/", "/index"   // 首页
                        , "/news/**" // 新闻
                )
                .permitAll()  // 设置无保护机制的路由或页面
                .anyRequest()   // 任意一个请求
                .authenticated()
                .and()
                .csrf() // 关闭csrf攻击防护
                .disable();
    }

    /**
     * 排除静态资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/js/**")
                .antMatchers("/images/**")
                .antMatchers("/layui/**")
                .antMatchers("/fonts/**");
    }
}
