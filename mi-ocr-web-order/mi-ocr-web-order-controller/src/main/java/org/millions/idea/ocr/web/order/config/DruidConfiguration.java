/***
 * @pName mi-ocr-web-finance
 * @name DruidConfiguration
 * @user HongWei
 * @date 2018/6/22
 * @desc
 */
package org.millions.idea.ocr.web.order.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

@Configuration
public class DruidConfiguration {
    private Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource druidDataSource(
    ) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setInitialSize(16);
        dataSource.setMaxActive(16);
        dataSource.setQueryTimeout(60);
        dataSource.setTransactionQueryTimeout(60);
        dataSource.setMinIdle(16);
        return dataSource;
    }
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        registrationBean.addInitParameter("allow", "127.0.0.1");// IP白名单 (没有配置或者为空，则允许所有访问)
        registrationBean.addInitParameter("deny", "");// IP黑名单 (存在共同时，deny优先于allow)
        registrationBean.addInitParameter("loginUsername", "admin");
        registrationBean.addInitParameter("loginPassword", "admin");
        registrationBean.addInitParameter("resetEnable", "false");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean druidWebStatViewFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new WebStatFilter());
        registrationBean.addInitParameter("urlPatterns", "/*");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return registrationBean;
    }
}
