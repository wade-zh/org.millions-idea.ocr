/***
 * @pName mi-ocr-web-app
 * @name Security
 * @user HongWei
 * @date 2018/7/22
 * @desc
 */
package org.millions.idea.ocr.app.entity.config;

public class Security {
    private String loginPage;
    private String processingUrl;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getProcessingUrl() {
        return processingUrl;
    }

    public void setProcessingUrl(String processingUrl) {
        this.processingUrl = processingUrl;
    }
}
