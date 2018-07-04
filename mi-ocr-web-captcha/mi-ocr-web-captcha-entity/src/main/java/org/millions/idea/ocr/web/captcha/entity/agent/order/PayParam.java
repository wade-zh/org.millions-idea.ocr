/***
 * @pName mi-ocr-web-captcha
 * @name PayParam
 * @user HongWei
 * @date 2018/7/2
 * @desc
 */
package org.millions.idea.ocr.web.captcha.entity.agent.order;


import java.math.BigDecimal;

public class PayParam {
    private Integer uid;
    private BigDecimal unitPrice;
    private Integer type;
    private String remark;
    private Integer fromUid;
    private Exceptions exceptions;
    private String captchaId;
    private String recordId;

    public PayParam() {
    }

    public Integer getUid() {

        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getFromUid() {
        return fromUid;
    }

    public void setFromUid(Integer fromUid) {
        this.fromUid = fromUid;
    }

    public Exceptions getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exceptions exceptions) {
        this.exceptions = exceptions;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public PayParam(Integer uid, BigDecimal unitPrice, Integer type, String remark, Integer fromUid, Exceptions exceptions, String captchaId, String recordId) {

        this.uid = uid;
        this.unitPrice = unitPrice;
        this.type = type;
        this.remark = remark;
        this.fromUid = fromUid;
        this.exceptions = exceptions;
        this.captchaId = captchaId;
        this.recordId = recordId;
    }
}
