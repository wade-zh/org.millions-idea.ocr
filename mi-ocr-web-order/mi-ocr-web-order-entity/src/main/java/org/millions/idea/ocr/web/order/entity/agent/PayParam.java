/***
 * @pName mi-ocr-web-order
 * @name PayParam
 * @user HongWei
 * @date 2018/6/30
 * @desc 订单参数类
 */
package org.millions.idea.ocr.web.order.entity.agent;

import org.millions.idea.ocr.web.order.entity.enums.transfer.Exceptions;

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
    private String channelId;

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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public PayParam() {

    }

    public PayParam(Integer uid, BigDecimal unitPrice, Integer type, String remark, Integer fromUid, Exceptions exceptions, String captchaId, String recordId, String channelId) {

        this.uid = uid;
        this.unitPrice = unitPrice;
        this.type = type;
        this.remark = remark;
        this.fromUid = fromUid;
        this.exceptions = exceptions;
        this.captchaId = captchaId;
        this.recordId = recordId;
        this.channelId = channelId;
    }
}
