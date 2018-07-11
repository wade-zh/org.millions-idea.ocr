/***
 * @pName mi-ocr-web-app
 * @name ThymeleafContextConfiguration
 * @user HongWei
 * @date 2018/7/11
 * @desc
 */
package org.millions.idea.ocr.app.ui.config;


import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class ThymeleafContextConfiguration {


    /**
     * 配置全局变量
     * @author HongWei
     * @param viewResolver
     */
    @Resource
    private void configureThymeleafVars(ThymeleafViewResolver viewResolver) {
        if(viewResolver == null) return;

        Map<String, Object> vars = new HashMap<>();

        // 提取前端模板引擎静态配置信息
        extractTemplateStaticVars(vars);

        // 提取域名列表中的信息
        extractDomainManager(vars);

        // 提取多页面动态配置信息
        extractMultiPageSettings(vars);

        viewResolver.setStaticVariables(vars);
    }

    /**
     * 提取多页面动态配置信息
     * @param vars
     */
    private void extractMultiPageSettings(Map<String, Object> vars) {
        //mapDatasService.FetchLoginPageConfigs().stream().forEach(data -> { vars.put(data.getMKey(), data.getMValue()); });
    }

    /**
     * 提取域名列表中的信息
     * @param vars
     */
    private void extractDomainManager(Map<String, Object> vars) {
        vars.put("domain_resource", "");
    }

    /**
     * 提取前端模板引擎静态配置信息
     * @param vars
     */
    private void extractTemplateStaticVars(Map<String, Object> vars) {
        vars.put("link","shared/link");
        vars.put("script","shared/script");
    }
}